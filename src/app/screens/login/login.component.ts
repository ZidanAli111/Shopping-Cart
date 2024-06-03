import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user/user';
import { UserLoginService } from 'src/app/service/userLogin/userlogin.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User = { userId: 0, username: '', password: '' };
  passwordVisible = true;
  loading = false;
  errorMessage: string | null = null;

  constructor(
    private userLoginService: UserLoginService,
    private router: Router
  ) { }

  ngOnInit(): void { }

  userLogin() {
    if (!this.user.username || !this.user.password) {
      this.errorMessage = "Username and Password are required";
      return;
    }

    this.loading = true;
    this.userLoginService.login(this.user).subscribe(
      (data: User) => {
        this.loading = false;
        const userData = btoa(JSON.stringify(data));
        localStorage.setItem('currentUser', userData);
        alert("Login Successful");
        this.router.navigate(['/item']);
      },
      error => {
        this.loading = false;
        this.errorMessage = "Login error occurred!!!";
      }
    );
  }

  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }
}
