package com.app.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.model.ItemDetails;
import com.app.repository.ItemRepository;
import com.app.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemRepository itemRepository;

	@Override
	public ResponseEntity<List<ItemDetails>> getAllItems() {

		try {
			return new ResponseEntity<>(itemRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

	}

	@Override
	public ItemDetails getItemBySku(String sku) {
		return itemRepository.findItemBySku(sku);
	}
	
	

}
