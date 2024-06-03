import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { of, throwError } from 'rxjs';


import { ItemService } from 'src/app/service/item/item.service';
import { Item } from 'src/app/model/item/item';
import { ItemComponent } from '../item/item.component';

describe('ItemComponent', () => {
  let component: ItemComponent;
  let fixture: ComponentFixture<ItemComponent>;
  let itemService: ItemService;
  let router: Router;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ItemComponent],
      imports: [HttpClientTestingModule],
      providers: [
        ItemService,
        { provide: Router, useValue: { navigate: jasmine.createSpy('navigate') } }
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ItemComponent);
    component = fixture.componentInstance;
    itemService = TestBed.inject(ItemService);
    router = TestBed.inject(Router);
    httpMock = TestBed.inject(HttpTestingController);

    fixture.detectChanges();
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should retrieve user data from local storage on init', () => {
    spyOn(localStorage, 'getItem').and.callFake((key: string) => {
      if (key === 'currentUser') {
        return btoa(JSON.stringify({ username: 'testuser', userId: 1 }));
      }
      return null;
    });

    component.ngOnInit();
    expect(component.username).toBe('testuser');
    expect(component.userId).toBe(1);
  });

  it('should navigate to login if user is not logged in', () => {
    spyOn(localStorage, 'getItem').and.returnValue(null);
    component.ngOnInit();
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  });

  it('should get items on init', () => {
    const mockItems: Item[] = [
      {
        sku: 'item1', itemDescription: 'Item 1', itemQuantity: 1, itemCost: 100,
        mfrNo: 'jklhghkm',
        vendorNo: 111
      },
      {
        sku: 'item2', itemDescription: 'Item 2', itemQuantity: 2, itemCost: 200,
        mfrNo: 'cbghjkn',
        vendorNo: 222
      }
    ];

    spyOn(itemService, 'getItemList').and.returnValue(of(mockItems));
    component.ngOnInit();
    expect(component.items).toEqual(mockItems);
  });

  it('should add item to cart', () => {
    const mockItem: Item = {
      sku: 'item1', itemDescription: 'Item 1', itemQuantity: 1, itemCost: 100,
      mfrNo: 'jklhghkm',
      vendorNo: 111
    };
    spyOn(itemService, 'addToCart').and.returnValue(of({}));

    component.userId = 1;
    component.addToCart(mockItem);

    expect(itemService.addToCart).toHaveBeenCalledWith(1, 'item1', 1);
  });

  it('should alert if user is not logged in when adding item to cart', () => {
    spyOn(window, 'alert');
    component.userId = null;
    const mockItem: Item = {
      sku: 'item1', itemDescription: 'Item 1', itemQuantity: 1, itemCost: 100,
      mfrNo: 'jklhghkm',
      vendorNo: 111
    };
    component.addToCart(mockItem);
    expect(window.alert).toHaveBeenCalledWith('User is not logged in!');
  });

  it('should navigate to usercart when displayCartItem is called', () => {
    component.displayCartItem();
    expect(router.navigate).toHaveBeenCalledWith(['/usercart']);
  });

  it('should handle error when failing to get items', () => {
    spyOn(window, 'alert');
    spyOn(itemService, 'getItemList').and.returnValue(throwError('Item error occurred'));

    component.ngOnInit();

    expect(window.alert).toHaveBeenCalledWith('Item error occurred!!!');
  });
 
  
});
