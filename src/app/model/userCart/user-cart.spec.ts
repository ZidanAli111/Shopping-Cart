import { UserCart } from './user-cart';

describe('UserCart', () => {

  it('should create an instance', () => {
    const userCart = new UserCart();
    expect(userCart).toBeTruthy();
  });

  it('should allow setting and getting properties', () => {
    const userCart = new UserCart();
    const userId = 1;
    const sku = '12345';
    const itemQuantity = 10;
    const itemDescription = 'Test item description';
    const itemCost = 99.99;
    const status = 'in-cart';

    userCart.userId = userId;
    userCart.sku = sku;
    userCart.itemQuantity = itemQuantity;
    userCart.itemDescription = itemDescription;
    userCart.itemCost = itemCost;
    userCart.status = status;

    expect(userCart.userId).toBe(userId);
    expect(userCart.sku).toBe(sku);
    expect(userCart.itemQuantity).toBe(itemQuantity);
    expect(userCart.itemDescription).toBe(itemDescription);
    expect(userCart.itemCost).toBe(itemCost);
    expect(userCart.status).toBe(status);
  });

  it('should handle undefined properties', () => {
    const userCart = new UserCart();
    expect(userCart.userId).toBeUndefined();
    expect(userCart.sku).toBeUndefined();
    expect(userCart.itemQuantity).toBeUndefined();
    expect(userCart.itemDescription).toBeUndefined();
    expect(userCart.itemCost).toBeUndefined();
    expect(userCart.status).toBeUndefined();
  });

});
