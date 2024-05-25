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
  passwordVisible = false;

  constructor(
    private userLoginService: UserLoginService,
    private router: Router
  ) { }

  ngOnInit(): void { }

  userLogin() {
    this.userLoginService.login(this.user).subscribe(
      (data: User) => {
        // Encode user data in Base64
        const userData = btoa(JSON.stringify(data));
        // Store encoded user data in local storage
        localStorage.setItem('currentUser', userData);
        console.log("User login successfully ", data);
        alert("Login Successful");
        // Navigate to the item screen
        this.router.navigate(['/item']);
      },
      error => alert("Login error occurred!!!")
    );
  }

  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }

}
