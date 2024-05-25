import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Item } from 'src/app/model/item/item';
import { ItemService } from 'src/app/service/item/item.service';
import { UserLoginService } from 'src/app/service/userLogin/userlogin.service';

@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
export class ItemComponent implements OnInit {

  items: Item[] | undefined;
  username: string | undefined;
  userId: number | null = null;

  constructor(
    private itemService: ItemService,
    private userLoginService: UserLoginService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getItem();

    // Retrieve user data from local storage
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

  private getItem() {
    this.itemService.getItemList().subscribe(data => {
      this.items = data;
    }, error => alert("Item error occurred!!!"));
  }

  increaseQuantity(item: Item): void {
    if (item && item.itemQuantity !== undefined) {
      item.itemQuantity++;
    }
  }

  addToCart(item: Item): void {
    if (this.userId !== null) {
      const userId = this.userId; // Use the current userId
      const sku = item.sku;
      const itemQuantity = item.itemQuantity ?? 1;

      this.itemService.addToCart(userId, sku, itemQuantity).subscribe(
        response => {
          console.log('Item added to cart successfully:', response);
          alert('Item added to cart successfully!');
        },
        error => {
          console.error('Error adding item to cart:', error);
          alert('Failed to add item to cart. Please try again.');
        }
      );
    } else {
      alert('User is not logged in!');
    }
  }

  displayCartItem(): void {
    this.router.navigate(['/usercart']);
  }
}
