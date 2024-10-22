package com.app.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.app.model.ItemDetails;

public interface ItemService {

	ResponseEntity<List<ItemDetails>> getAllItems();
	
	ItemDetails getItemBySku(String sku);
}
