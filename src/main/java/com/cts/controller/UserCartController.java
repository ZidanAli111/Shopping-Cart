package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.model.UserCartDetails;
import com.cts.repository.UserCartRepository;
import com.cts.service.ItemService;
import com.cts.service.UserCartService;


@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/usercart")
public class UserCartController {

	@Autowired
	UserCartService userCartService;

	@Autowired
	ItemService itemService;

	@Autowired
	UserCartRepository userCartRepository;

	@PostMapping("/addtocart")
	public ResponseEntity<String> addToCart(@RequestParam int userId, @RequestParam String sku,
			@RequestParam int itemQuantity) {
		try {
			userCartService.addToCart(userId, sku, itemQuantity);

			return ResponseEntity.ok("Item added to cart successfully");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while processing request!");
		}
	}

	@GetMapping("/retrieveusercart")
	public ResponseEntity<List<UserCartDetails>> getUserCartItems() {
		return userCartService.getAllUserCartItems();
	}

	@PutMapping("/modifyusercart")
	public ResponseEntity<String> modifyUserCartItem(@RequestParam int userId, @RequestParam String sku,
			@RequestBody UserCartDetails userCartDetails) {

		userCartService.modifyItemQuantity(userId, sku, userCartDetails);
		return ResponseEntity.ok("Item quantity modified successfully");

	}
	
	@GetMapping("/retrieveusercart/{userId}")
	 public ResponseEntity<List<UserCartDetails>> getAllUserCartItems(@PathVariable int userId) {
       try {
           return userCartService.getUserCartItems(userId);
       } catch (Exception e) {
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
       }
   }
}
