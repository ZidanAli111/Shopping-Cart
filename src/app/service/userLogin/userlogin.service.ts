import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../model/user/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserloginService {

  private username: string | undefined;

  private baseUrl = "http://localhost:9081/api/login";

  constructor(private httpClient: HttpClient) { }

  loginUser(user: User): Observable<object> {
    console.log(user);
    return this.httpClient.post(this.baseUrl, user);
  }

  setUsername(username: string): void {
    this.username = username;
  }

  getUsername(): string | undefined {
    return this.username;
  }
}

