package com.cts.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.app.model.UserCartDetails;

public class UserCartDetailsTest {

	@Test
	public void testGetterAndSetters() {
		UserCartDetails userCartDetails = new UserCartDetails();

		userCartDetails.setSku("14567");
		userCartDetails.setStatus('A');
		userCartDetails.setItemQuantity(2);
		userCartDetails.setItemCost(2340);
		userCartDetails.setItemDescription("Smooth face wields nailing hammer");
		userCartDetails.setUserId(1);

		assertEquals("14567", userCartDetails.getSku());
		assertEquals('A', userCartDetails.getStatus());
		assertEquals(2, userCartDetails.getItemQuantity());
		assertEquals(2340, userCartDetails.getItemCost());
		assertEquals("Smooth face wields nailing hammer", userCartDetails.getItemDescription());
		assertEquals(1, userCartDetails.getUserId());

	}
}
