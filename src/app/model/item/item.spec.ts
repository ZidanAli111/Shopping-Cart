import { Item } from './item';

describe('Item', () => {

  it('should create an instance', () => {
    const item = new Item();
    expect(item).toBeTruthy();
  });

  it('should allow setting and getting properties', () => {
    const item = new Item();
    const sku = '12345';
    const itemDescription = 'Test item description';
    const itemQuantity = 10;
    const itemCost = 99.99;
    const mfrNo = 'MFR123';
    const vendorNo = 456;

    item.sku = sku;
    item.itemDescription = itemDescription;
    item.itemQuantity = itemQuantity;
    item.itemCost = itemCost;
    item.mfrNo = mfrNo;
    item.vendorNo = vendorNo;

    expect(item.sku).toBe(sku);
    expect(item.itemDescription).toBe(itemDescription);
    expect(item.itemQuantity).toBe(itemQuantity);
    expect(item.itemCost).toBe(itemCost);
    expect(item.mfrNo).toBe(mfrNo);
    expect(item.vendorNo).toBe(vendorNo);
  });

  it('should handle undefined values', () => {
    const item = new Item();
    expect(item.itemDescription).toBeUndefined();
    expect(item.itemQuantity).toBeUndefined();
    expect(item.itemCost).toBeUndefined();
    expect(item.mfrNo).toBeUndefined();
    expect(item.vendorNo).toBeUndefined();
  });

});

