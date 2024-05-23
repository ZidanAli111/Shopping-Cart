import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserCart } from 'src/app/model/userCart/user-cart';
import { UserCartService } from 'src/app/service/userCart/user-cart.service';
import { UserloginService } from 'src/app/service/userLogin/userlogin.service';

@Component({
  selector: 'app-user-cart',
  templateUrl: './user-cart.component.html',
  styleUrls: ['./user-cart.component.css']
})
export class UserCartComponent implements OnInit {

  userCart: UserCart[] | undefined;
  username: string | undefined;

  constructor(private userCartService: UserCartService,
    private userLoginService: UserloginService,
    private router: Router) { }

  ngOnInit(): void {
    this.username = this.userLoginService.getUsername();
    this.getCartItem();
  }

  private getCartItem() {
    if (this.username) {
      this.userCartService.getItemList().subscribe(data => {
        this.userCart = data;
      }, error => ("Cart item not found!!!")
      );
    }
  }

  adjustQuantity(item: UserCart): void {
    // Logic to adjust the quantity of the item
    console.log('Adjusting quantity for:', item);
    alert('Quantity updated successfully!');

  }

  addToCart(item: UserCart): void {
    console.log('Adding to cart:', item);
    alert('item adeed to cart successfully!');

  }



}
