package com.cts.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.app.model.ItemDetails;

public class ItemDetailsTest {

	@Test
	public void testGettersAndSetters() {
		ItemDetails item = new ItemDetails();
		item.setId(1);
		item.setSku("ABC123");
		item.setItemDescription("Test description");
		item.setItemQuantity(10);
		item.setItemCost(20.5f);
		item.setMfrNo("XYZ456");
		item.setVendorNo(123);

		assertEquals(1, item.getId());
		assertEquals("ABC123", item.getSku());
		assertEquals("Test description", item.getItemDescription());
		assertEquals(10, item.getItemQuantity());
		assertEquals(20.5f, item.getItemCost(), 0.001);
		assertEquals("XYZ456", item.getMfrNo());
		assertEquals(123, item.getVendorNo());
	}
}
