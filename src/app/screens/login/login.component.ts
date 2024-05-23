import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user/user';
import { UserloginService } from 'src/app/service/userLogin/userlogin.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {


  user: User = { username: '', password: '' };

  constructor(private userLoginService: UserloginService,
    private router: Router) { }

  ngOnInit(): void {
  }

 

  userLogin() {
    this.userLoginService.loginUser(this.user).subscribe((data :any)=> {
      console.log("User login successfully ", data);
      alert("Login Successfull");
      this.userLoginService.setUsername(this.user.username!);
      this.router.navigate(['/item']);
    }, error => alert("Login error occured!!!")
    
    );
  }
}
