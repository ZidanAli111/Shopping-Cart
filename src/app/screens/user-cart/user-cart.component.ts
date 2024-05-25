import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserCart } from 'src/app/model/userCart/user-cart';
import { UserCartService } from 'src/app/service/userCart/user-cart.service';
import { UserLoginService } from 'src/app/service/userLogin/userlogin.service';

@Component({
  selector: 'app-user-cart',
  templateUrl: './user-cart.component.html',
  styleUrls: ['./user-cart.component.css']
})
export class UserCartComponent implements OnInit {

  userCart: UserCart[] | undefined;
  username: string | null = null;

  constructor(
    private userCartService: UserCartService,
    private userLoginService: UserLoginService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.username = this.userLoginService.getUsername();
    this.getCartItem();
  }

  private getCartItem() {
    if (this.username) {
      this.userCartService.getItemList().subscribe(data => {
        this.userCart = data;
      }, error => console.error("Cart item not found!!!"));
    }
  }

  modifyQuantity(item: UserCart): void {
    console.log('Adjusting quantity for:', item);
    alert('Quantity updated successfully!');
  }

  addToCheckout(item: UserCart): void {
    console.log('Adding to cart:', item);
    alert('Item added to checkout successfully!');
  }

  getTotalCost(): number {
    return this.userCart?.reduce((total, item) => total + (item.itemQuantity || 0) * (item.itemCost || 0), 0) || 0;
  }

  checkout(): void {
    alert('Proceeding to checkout!');
    this.router.navigate(['/item']);
  }

  removeFromCart(item: UserCart): void {
    console.log('Removing from cart:', item);
    alert('Item removed from cart successfully!');
    this.userCart = this.userCart?.filter(cartItem => cartItem.sku !== item.sku);
  }
}
