import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Item } from 'src/app/model/item/item';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  private baseUrl="http://localhost:9081/api/items/loadpurchaseitems";

  constructor(private httpClient:HttpClient) { }

  getItemList(): Observable<Item[]>{
    return this.httpClient.get<Item[]>(`${this.baseUrl}`);
  }

}
