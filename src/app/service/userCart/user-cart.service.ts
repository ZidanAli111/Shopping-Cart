import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserCart } from 'src/app/model/userCart/user-cart';

@Injectable({
  providedIn: 'root'
})
export class UserCartService {

  private baseUrl = "http://localhost:9081/api/usercart/retrieveusercart";

  constructor(private httpClient: HttpClient) { }

  getItemList(): Observable<UserCart[]> {
    return this.httpClient.get<UserCart[]>(`${this.baseUrl}`);
  }
}
