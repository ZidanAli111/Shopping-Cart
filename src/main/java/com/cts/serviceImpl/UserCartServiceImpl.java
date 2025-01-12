package com.cts.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.model.ItemDetails;
import com.cts.model.UserCartDetails;
import com.cts.model.UserDetails;
import com.cts.repository.ItemRepository;
import com.cts.repository.UserCartRepository;
import com.cts.repository.UserRepository;
import com.cts.service.UserCartService;

@Service
public class UserCartServiceImpl implements UserCartService {

	@Autowired
	UserCartRepository userCartRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ItemRepository itemRepository;

	@Override
	public void addToCart(int userId, String sku, int itemQuantity) {
		Optional<UserDetails> userOptional = userRepository.findById(userId);
		ItemDetails itemDetails = itemRepository.findItemBySku(sku);

		if (userOptional.isPresent()) {
			UserDetails userDetails = userOptional.get();
			Optional<UserCartDetails> optionalUserCartDetails = userCartRepository.findByUserDetailsAndSku(userDetails,
					sku);

			if (optionalUserCartDetails.isPresent()) {
				// Item is already present in the cart, increment the quantity by 1
				UserCartDetails userCartDetails = optionalUserCartDetails.get();
				int updatedQuantity = userCartDetails.getItemQuantity() + 1;
				userCartDetails.setItemQuantity(updatedQuantity);
				userCartRepository.save(userCartDetails);
			} else {
				// Item not present in cart, add it with the given quantity
				if (itemDetails != null) {
					UserCartDetails newUserCartDetails = new UserCartDetails();
					newUserCartDetails.setUserDetails(userDetails);
					newUserCartDetails.setSku(sku);
					newUserCartDetails.setItemQuantity(itemQuantity);
					newUserCartDetails.setItemDescription(itemDetails.getItemDescription());
					newUserCartDetails.setItemCost(itemDetails.getItemCost());
					newUserCartDetails.setStatus('A');
					userCartRepository.save(newUserCartDetails);
				} else {
					throw new IllegalArgumentException("Item not found!!");
				}
			}
		} else {
			throw new IllegalArgumentException("User not found");
		}
	}

	@Override
	public ResponseEntity<List<UserCartDetails>> getAllUserCartItems() {
		try {
			return new ResponseEntity<>(userCartRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	@Override
	public void modifyItemQuantity(int userId, String sku, UserCartDetails updatedUserCartDetails) {
		Optional<UserDetails> optionalUserDetails = userRepository.findById(userId);

		if (optionalUserDetails.isPresent()) {
			UserDetails userDetails = optionalUserDetails.get();
			Optional<UserCartDetails> optionalExistingUserCartDetails = userCartRepository
					.findByUserDetailsAndSku(userDetails, sku);

			if (optionalExistingUserCartDetails.isPresent()) {
				UserCartDetails existingUserCartDetails = optionalExistingUserCartDetails.get();
				existingUserCartDetails.setItemQuantity(updatedUserCartDetails.getItemQuantity());
				userCartRepository.save(existingUserCartDetails);

			} else {
				throw new IllegalArgumentException("Item  not found in user's cart");
			}
		} else {
			throw new IllegalArgumentException("User not found");
		}

	}

	@Override
	public ResponseEntity<List<UserCartDetails>> getUserCartItems(int userId) {
		try {
			List<UserCartDetails> userCartItems = userCartRepository.findByUserDetailsUserId(userId);
			return ResponseEntity.ok(userCartItems);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
		}
	}
}
