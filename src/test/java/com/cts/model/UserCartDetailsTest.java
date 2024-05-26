package com.cts.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserCartDetailsTest {

	@Test
	public void testGetterAndSetters() {
		UserCartDetails userCartDetails=new UserCartDetails();
		
		userCartDetails.setSku("14567");
		userCartDetails.setStatus('A');
		userCartDetails.setItemQuantity(2);
		userCartDetails.setItemCost(2340);
		
		
		
		assertEquals("14567",userCartDetails.getSku());
		assertEquals('A',userCartDetails.getStatus());
		assertEquals(2,userCartDetails.getItemQuantity());
		assertEquals(2340,userCartDetails.getItemCost());
	
	}
}
