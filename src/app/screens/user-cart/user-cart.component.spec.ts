import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UserCartComponent } from './user-cart.component';
import { Router } from '@angular/router';
import { UserCartService } from 'src/app/service/userCart/user-cart.service';
import { of } from 'rxjs';
import { UserCart } from 'src/app/model/userCart/user-cart';

describe('UserCartComponent', () => {
  let component: UserCartComponent;
  let fixture: ComponentFixture<UserCartComponent>;
  let userCartService: jasmine.SpyObj<UserCartService>;
  let router: Router;

  beforeEach(async () => {
    const userCartServiceSpy = jasmine.createSpyObj('UserCartService', ['getItemList', 'modifyItemQuantity']);
    await TestBed.configureTestingModule({
      declarations: [UserCartComponent],
      providers: [
        { provide: UserCartService, useValue: userCartServiceSpy },
        { provide: Router, useValue: { navigate: jasmine.createSpy('navigate') } }
      ]
    }).compileComponents();

    userCartService = TestBed.inject(UserCartService) as jasmine.SpyObj<UserCartService>;
    router = TestBed.inject(Router);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserCartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    component.userId = 1; // Initialize userId
    const userCartData: UserCart[] = [{
      userId: 1,
      sku: 'acb134',
      itemQuantity: 1,
      itemDescription: 'aaa',
      itemCost: 10,
      status: 'A'
    }];
    userCartService.getItemList.and.returnValue(of(userCartData));
  
    fixture.detectChanges();
  
    expect(component).toBeTruthy();
  });
  
  it('should modify item quantity', () => {
    component.userId = 1; // Initialize userId
    const item: UserCart = { userId: 1, sku: 'acb134', itemQuantity: 2, itemDescription: 'aaa', itemCost: 10, status: 'A' };
    const userCartData: UserCart[] = [item];
    userCartService.getItemList.and.returnValue(of(userCartData));
    userCartService.modifyItemQuantity.and.returnValue(of('Success'));
    
    spyOn(window, 'alert');
    
    component.modifyQuantity(item);
  
    expect(userCartService.modifyItemQuantity).toHaveBeenCalledWith(1, 'item1', item);
    expect(window.alert).toHaveBeenCalledWith('Item quantity modified!!!');
  });
  
  
  // Add more test cases as needed...
});
