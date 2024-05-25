package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.model.ItemDetails;
import com.cts.service.ItemService;


@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/items")
public class ItemController {

	@Autowired
	ItemService itemService;

	@GetMapping("/loadpurchaseitems")
	public ResponseEntity<List<ItemDetails>> loadPurchaseItems() {
		return itemService.getAllItems();

	}
}
