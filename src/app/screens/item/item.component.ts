import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Item } from 'src/app/model/item/item';
import { User } from 'src/app/model/user/user';
import { ItemService } from 'src/app/service/item/item.service';
import { UserloginService } from 'src/app/service/userLogin/userlogin.service';


@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
export class ItemComponent implements OnInit {

  item: Item[] | undefined;
  user: User | undefined;
  username: string | undefined;

  constructor(private itemService: ItemService, private userLoginService: UserloginService, private router: Router) { }

  ngOnInit(): void {
    this.getItem();
    // this.username = this.userLoginService.getUsername();
     this.username="Zidan"
     
  }

  private getItem() {
    this.itemService.getItemList().subscribe(data => {
      this.item = data;
      //  alert("Item found!!!");
    }, error => alert("Item error occured!!!"));
  }


  adjustQuantity(item: Item): void {
    // Logic to adjust the quantity of the item
    console.log('Adjusting quantity for:', item);
    alert('Quantity updated successfully!');

  }

  addToCart(item: Item): void {
    console.log('Adding to cart:', Item);
    alert('item adeed to cart successfully!');
  }

  displayCartItem():void{
    console.log("item clicked");
    this.router.navigate(['/usercart']);
  }
}
