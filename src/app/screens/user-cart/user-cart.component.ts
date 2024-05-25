import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserCart } from 'src/app/model/userCart/user-cart';
import { UserCartService } from 'src/app/service/userCart/user-cart.service';

@Component({
  selector: 'app-user-cart',
  templateUrl: './user-cart.component.html',
  styleUrls: ['./user-cart.component.css']
})
export class UserCartComponent implements OnInit {

  userCart: UserCart[] | undefined;
  username: string | null = null;
  userId: number | null = null;

  constructor(
    private userCartService: UserCartService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.retrieveUserData();
    this.getCartItem();
  }

  private retrieveUserData(): void {
    const storedUser = localStorage.getItem('currentUser');
    if (storedUser) {
      const user = JSON.parse(atob(storedUser));
      this.username = user.username;
      this.userId = user.userId;
    } else {
      alert('User is not logged in!');
      this.router.navigate(['/login']);
    }
  }

  private getCartItem(): void {
    if (this.userId !== null) {
      this.userCartService.getItemList(this.userId).subscribe(data => {
        this.userCart = data;
      }, error => alert("Item error occurred!!!"));
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
