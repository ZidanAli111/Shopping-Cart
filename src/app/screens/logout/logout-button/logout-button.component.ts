import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout-button',
  templateUrl: './logout-button.component.html',
  styleUrls: ['./logout-button.component.css']
})
export class LogoutButtonComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  logout(): void {
    // Implement logout functionality here
    // For example, clear local storage and navigate to the login page
    localStorage.removeItem('currentUser');
    this.router.navigate(['/login']);
  }

}
