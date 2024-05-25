import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { Item } from 'src/app/model/item/item';

@Injectable({
  providedIn: 'root'
})
export class ItemService {
  private baseUrl = "http://localhost:9081/api/items/loadpurchaseitems";
  private addToCartUrl = "http://localhost:9081/api/usercart/addtocart";

  constructor(private httpClient: HttpClient) { }

  getItemList(): Observable<Item[]> {
    return this.httpClient.get<Item[]>(this.baseUrl);
  }

  addToCart(userId: number, sku: string, itemQuantity: number): Observable<any> {
    const finalUrl = `${this.addToCartUrl}?userId=${userId}&sku=${sku}&itemQuantity=${itemQuantity}`;
    console.log("final url", finalUrl);
    return this.httpClient.post(finalUrl, {}, { responseType: 'text' }).pipe(
      catchError(error => {
        console.error('Error adding item to cart:', error);
        throw error;
      })
    );
  }
}
