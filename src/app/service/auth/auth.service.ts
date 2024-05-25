import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { UserLoginService } from '../userLogin/userlogin.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {


  loginStatus: boolean = false;
  validLogin = new Subject<boolean>();

  constructor(
    private userLoginService: UserLoginService,
    private router: Router
  ) { }


  isAuthenticated() {
    const promise = new Promise(
      (resolve, reject) => {
        resolve(this.loginStatus);
      }
    );
    return promise;
  }

  onUserLogOut() {
    this.loginStatus = false;
    this.router.navigate(['/login']);
  }


  onUserLogin(userId: number) {
    this.userLoginService.getByUserId(userId).subscribe({
      next: (response) => {
        this.loginStatus = true;
        this.userLoginService.setLoggedInUser(response.userId);
        this.router.navigate(['/item']);
        this.validLogin.next(true);
      },
      error: (err) => {
        this.loginStatus = false;
        this.validLogin.next(false);
      }
    });
  }

}
