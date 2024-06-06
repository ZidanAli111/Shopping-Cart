import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { Item } from 'src/app/model/item/item';
import { ItemService } from 'src/app/service/item/item.service';
import { ItemComponent } from './item.component';
 
class MockItemService {
  getItemList() {
    return of([
      { sku: 'ABC123', itemDescription: 'Sample Item', itemQuantity: 10, itemCost: 100, mfrNo: 'MFR001', vendorNo: 1 },
      { sku: 'DEF456', itemDescription: 'Another Item', itemQuantity: 5, itemCost: 200, mfrNo: 'MFR002', vendorNo: 2 }
    ] as Item[]);
  }
  
  addToCart(userId: number, sku: string, itemQuantity: number) {
    return of({ message: 'Item added to cart successfully!' });
  }
}
 
describe('ItemComponent', () => {
  let component: ItemComponent;
  let fixture: ComponentFixture<ItemComponent>;
  let mockItemService: MockItemService;
  let mockRouter: jasmine.SpyObj<Router>;
 
  beforeEach(async () => {
    mockItemService = new MockItemService();
    mockRouter = jasmine.createSpyObj('Router', ['navigate']);
 
    await TestBed.configureTestingModule({
      declarations: [ItemComponent],
      providers: [
        { provide: ItemService, useValue: mockItemService },
        { provide: Router, useValue: mockRouter }
      ]
    }).compileComponents();
  });
 
  beforeEach(() => {
    fixture = TestBed.createComponent(ItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
 
  it('should create the component', () => {
    expect(component).toBeTruthy();
  });
 
  it('should fetch items on initialization', () => {
    spyOn(mockItemService, 'getItemList').and.callThrough();
    component.ngOnInit();
    expect(mockItemService.getItemList).toHaveBeenCalled();
    expect(component.items?.length).toBe(2);
  });
 
  it('should display user data if stored in local storage', () => {
    localStorage.setItem('currentUser', btoa(JSON.stringify({ username: 'testuser', userId: 1 })));
    component.ngOnInit();
    expect(component.username).toBe('testuser');
    expect(component.userId).toBe(1);
  });
 
  it('should navigate to login if user data is not in local storage', () => {
    localStorage.removeItem('currentUser');
    component.ngOnInit();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/login']);
  });
 
  it('should add item to cart successfully', () => {
    spyOn(mockItemService, 'addToCart').and.callThrough();
    component.userId = 1;
    const item: Item = { sku: 'ABC123', itemDescription: 'Sample Item', itemQuantity: 10, itemCost: 100, mfrNo: 'MFR001', vendorNo: 1 };
    component.addToCart(item);
    expect(mockItemService.addToCart).toHaveBeenCalledWith(1, 'ABC123', 10);
  });
 
  it('should show alert if user is not logged in when adding to cart', () => {
    spyOn(window, 'alert');
    component.userId = null;
    const item: Item = { sku: 'ABC123', itemDescription: 'Sample Item', itemQuantity: 10, itemCost: 100, mfrNo: 'MFR001', vendorNo: 1 };
    component.addToCart(item);
    expect(window.alert).toHaveBeenCalledWith('User is not logged in!');
  });
 
  it('should handle error when adding item to cart fails', () => {
    spyOn(mockItemService, 'addToCart').and.returnValue(throwError({ status: 500 }));
    spyOn(window, 'alert');
    component.userId = 1;
    const item: Item = { sku: 'ABC123', itemDescription: 'Sample Item', itemQuantity: 10, itemCost: 100, mfrNo: 'MFR001', vendorNo: 1 };
    component.addToCart(item);
    expect(window.alert).toHaveBeenCalledWith('Failed to add item to cart. Please try again.');
  });
});
