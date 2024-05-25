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

  loggedInUser!: number ;

  constructor(private http: HttpClient) { }


  getByUserId(userId: number) {
    return this.http.get<User>(this.loginUrl + '/userId' + userId);
  }

  login(user: User): Observable<User> {
    return this.http.post<User>(this.loginUrl, user);
  }

  setCurrentUser(user: User): void {
    this.currentUser = user;
  }

  getCurrentUser(): User | null {
    return this.currentUser;
  }

  getUserId(): number | null {
    return this.currentUser?.userId ?? null;
  }

  getUsername(): string | null {
    return this.currentUser?.username ?? null;
  }

  getLoggedInUser() {
    return this.loggedInUser;
  }

  setLoggedInUser(userId: number) {
    this.loggedInUser = userId;
  }
}
