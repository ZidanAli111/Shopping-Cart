import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ItemService } from './item.service';
import { Item } from 'src/app/model/item/item';

describe('ItemService', () => {
  let service: ItemService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ItemService]
    });
    service = TestBed.inject(ItemService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve items from the API via GET', () => {
    const dummyItems: Item[] = [
      { sku: 'item1', itemDescription: 'Item 1', itemQuantity: 10, itemCost: 100, mfrNo: 'MFR1', vendorNo: 1 },
      { sku: 'item2', itemDescription: 'Item 2', itemQuantity: 20, itemCost: 200, mfrNo: 'MFR2', vendorNo: 2 }
    ];

    service.getItemList().subscribe(items => {
      expect(items.length).toBe(2);
      expect(items).toEqual(dummyItems);
    });

    const req = httpMock.expectOne(service['baseUrl']);
    expect(req.request.method).toBe('GET');
    req.flush(dummyItems);
  });

  it('should add item to cart via POST', () => {
    const userId = 1;
    const sku = 'item1';
    const itemQuantity = 5;
    const response = 'Item added to cart';

    service.addToCart(userId, sku, itemQuantity).subscribe(res => {
      expect(res).toBe(response);
    });

    const req = httpMock.expectOne(`${service['addToCartUrl']}?userId=${userId}&sku=${sku}&itemQuantity=${itemQuantity}`);
    expect(req.request.method).toBe('POST');
    req.flush(response, { headers: { 'Content-Type': 'text/plain' } });
  });

  it('should handle error when adding item to cart', () => {
    const userId = 1;
    const sku = 'item1';
    const itemQuantity = 5;
    const errorMessage = '500 Internal Server Error';

    service.addToCart(userId, sku, itemQuantity).subscribe(
      () => fail('expected an error, not items'),
      (error) => {
        expect(error.status).toBe(500);
        expect(error.statusText).toBe('Internal Server Error');
      }
    );

    const req = httpMock.expectOne(`${service['addToCartUrl']}?userId=${userId}&sku=${sku}&itemQuantity=${itemQuantity}`);
    expect(req.request.method).toBe('POST');
    req.flush(errorMessage, { status: 500, statusText: 'Internal Server Error' });
  });
});
