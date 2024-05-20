package com.app.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.app.model.UserCartDetails;

public interface UserCartService {
	
	public void addToCart(int userId,String sku,int itemQuantity);
	
	ResponseEntity<List<UserCartDetails>> getAllUserCartItems();
	
//	public ResponseEntity<String>updateItemQuantity(int userId,String sku);
//
//	;
	
	void modifyItemQuantity(int userId,String sku,UserCartDetails updatedUserCartDetails);

}
