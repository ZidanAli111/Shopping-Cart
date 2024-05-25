package com.cts.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cts.model.UserCartDetails;

public interface UserCartService {
	
	public void addToCart(int userId,String sku,int itemQuantity);
	
	ResponseEntity<List<UserCartDetails>> getAllUserCartItems();
	
	 ResponseEntity<List<UserCartDetails>> getUserCartItems(int userId);

	
	void modifyItemQuantity(int userId,String sku,UserCartDetails updatedUserCartDetails);

}
