
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { UserCart } from 'src/app/model/userCart/user-cart';
import { UserCartService } from 'src/app/service/userCart/user-cart.service';
import { UserCartComponent } from './user-cart.component';
 
// Mock user cart data
const mockUserCartData: UserCart[] = [
  { userId: 1, sku: 'ABC123', itemQuantity: 2, itemDescription: 'Test Item', itemCost: 100, status: 'Active' },
  // Add more mock data if needed
];
 
// Mock UserCartService
class MockUserCartService {
  getItemList(userId: number) {
    return of(mockUserCartData);
  }
 
  modifyItemQuantity(userId: number, sku: string, userCart: UserCart) {
    return of('Item quantity modified!!!');
  }
}
 
describe('UserCartComponent', () => {
  let component: UserCartComponent;
  let fixture: ComponentFixture<UserCartComponent>;
  let mockUserCartService: MockUserCartService;
  let mockRouter: jasmine.SpyObj<Router>;
 
  beforeEach(async () => {
    mockUserCartService = new MockUserCartService();
    mockRouter = jasmine.createSpyObj('Router', ['navigate']);
 
    await TestBed.configureTestingModule({
      declarations: [UserCartComponent],
      providers: [
        { provide: UserCartService, useClass: MockUserCartService },
        { provide: Router, useValue: mockRouter }
      ]
    }).compileComponents();
  });
 
  beforeEach(() => {
    fixture = TestBed.createComponent(UserCartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
 
  it('should create the component', () => {
    expect(component).toBeTruthy();
  });
 
  it('should retrieve user data on initialization', () => {
    const storedUser = { userId: 1, username: 'testuser' };
    localStorage.setItem('currentUser', btoa(JSON.stringify(storedUser)));
    component.ngOnInit();
    expect(component.username).toBe('testuser');
    expect(component.userId).toBe(1);
  });
 
  it('should navigate to login if user data is not available', () => {
    localStorage.removeItem('currentUser');
    component.ngOnInit();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/login']);
  });
 
  it('should retrieve cart items on initialization if userId is available', () => {
    const storedUser = { userId: 1, username: 'testuser' };
    localStorage.setItem('currentUser', btoa(JSON.stringify(storedUser)));
    component.ngOnInit();
    expect(component.userCart).toEqual(mockUserCartData);
  });
 
  it('should modify quantity of an item', () => {
    const userCart: UserCart = { userId: 1, sku: 'ABC123', itemQuantity: 2, itemDescription: 'Test Item', itemCost: 100, status: 'Active' };
    component.userId = 1;
    spyOn(window, 'alert');
    component.modifyQuantity(userCart);
    expect(window.alert).toHaveBeenCalledWith('Item quantity modified!!!');
  });
});