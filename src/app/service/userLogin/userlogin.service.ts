import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from 'src/app/model/user/user';

@Injectable({
  providedIn: 'root'
})
export class UserLoginService {

  loginUrl = 'http://localhost:9081/api/login';

  currentUser: User | null = null;

  constructor(private http: HttpClient) { }

  login(user: User): Observable<User> {
    console.log("user :", user);
    return this.http.post<User>(this.loginUrl, user);
  }

  setCurrentUser(user: User): void {
    this.currentUser = user;
    localStorage.setItem('currentUser', btoa(JSON.stringify(user)));
  }

  getCurrentUser(): User | null {
    const userData = localStorage.getItem('currentUser');
    return userData ? JSON.parse(atob(userData)) : null;
  }

  getUserId(): number | null {
    return this.getCurrentUser()?.userId ?? null;
  }

  getUsername(): string | null {
    return this.getCurrentUser()?.username ?? null;
  }
}
