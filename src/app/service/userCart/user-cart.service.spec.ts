import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserCartService } from './user-cart.service';
import { UserCart } from 'src/app/model/userCart/user-cart';

describe('UserCartService', () => {
  let service: UserCartService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserCartService]
    });
    service = TestBed.inject(UserCartService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve user cart items via GET', () => {
    const userId = 1;
    const dummyUserCart: UserCart[] = [
      { userId: 1, sku: 'item1', itemQuantity: 10, itemDescription: 'Item 1', itemCost: 100, status: 'in-cart' },
      { userId: 1, sku: 'item2', itemQuantity: 20, itemDescription: 'Item 2', itemCost: 200, status: 'in-cart' }
    ];

    service.getItemList(userId).subscribe(items => {
      expect(items.length).toBe(2);
      expect(items).toEqual(dummyUserCart);
    });

    const req = httpMock.expectOne(`${service['viewCartUrl']}${userId}`);
    expect(req.request.method).toBe('GET');
    req.flush(dummyUserCart);
  });

  it('should modify item quantity via PUT', () => {
    const userId = 1;
    const sku = 'item1';
    const userCart: UserCart = { userId: 1, sku: 'item1', itemQuantity: 5, itemDescription: 'Item 1', itemCost: 100, status: 'in-cart' };
    const response = 'Item quantity updated';

    service.modifyItemQuantity(userId, sku, userCart).subscribe(res => {
      expect(res).toBe(response);
    });

    const req = httpMock.expectOne(`${service['updateCartUrl']}?userId=${userId}&sku=${sku}`);
    expect(req.request.method).toBe('PUT');
    req.flush(response, { headers: { 'Content-Type': 'text/plain' } });
  });

  it('should handle error when modifying item quantity', () => {
    const userId = 1;
    const sku = 'item1';
    const userCart: UserCart = { userId: 1, sku: 'item1', itemQuantity: 5, itemDescription: 'Item 1', itemCost: 100, status: 'in-cart' };
    const errorMessage = '500 Internal Server Error';

    service.modifyItemQuantity(userId, sku, userCart).subscribe(
      () => fail('expected an error, not a success response'),
      (error) => {
        expect(error.status).toBe(500);
        expect(error.statusText).toBe('Internal Server Error');
      }
    );

    const req = httpMock.expectOne(`${service['updateCartUrl']}?userId=${userId}&sku=${sku}`);
    expect(req.request.method).toBe('PUT');
    req.flush(errorMessage, { status: 500, statusText: 'Internal Server Error' });
  });
});
