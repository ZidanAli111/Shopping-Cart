import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { UserCart } from 'src/app/model/userCart/user-cart';

@Injectable({
  providedIn: 'root'
})
export class UserCartService {

   private viewCartUrl = "http://localhost:9081/api/usercart/retrieveusercart/";
    private updateCartUrl="http://localhost:9081/api/usercart/modifyusercart";

  
  constructor(private httpClient: HttpClient) { }

  getItemList(userId: number): Observable<UserCart[]> {
    const finalUrl = this.viewCartUrl + userId;
    return this.httpClient.get<UserCart[]>(finalUrl);
  }

  modifyItemQuantity(userId: number, sku: string, userCart: UserCart){
    const finalUrl = `${this.updateCartUrl}?userId=${userId}&sku=${sku}`;

    console.log("PUT URL :"+finalUrl);
    return this.httpClient.put(finalUrl, userCart,{responseType:'text'});
  }
   
  }



