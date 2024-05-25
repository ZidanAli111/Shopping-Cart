import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { User } from 'src/app/model/user/user';
import { AuthService } from 'src/app/service/auth/auth.service';
import { UserLoginService } from 'src/app/service/userLogin/userlogin.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User = { userId: 0, username: '', password: '' };

  validLogin: boolean = true;

  passwordVisible=false;

  constructor(
    private authService: AuthService,
    private userLoginService:UserLoginService,
    private router: Router
  ) { }

  ngOnInit(): void { }

  userLogin() {
    this.userLoginService.login(this.user).subscribe((data: User) => {
      console.log("User login successfully ", data);
      alert("Login Successful");
      this.userLoginService.setCurrentUser(data);
      this.router.navigate(['/item']);
    }, error => alert("Login error occurred!!!"));
  }


  onLogin(form: NgForm) {

    this.authService.onUserLogin(form.value.loginId);
    this.authService.validLogin.subscribe(val => {
      this.validLogin = val;
    });

    console.log(this.validLogin);
  }


  togglePasswordVisibility(){
    this.passwordVisible=!this.passwordVisible;
  }





}
