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

			if (itemDetails != null) {
				UserDetails userDetails = userOptional.get();
				
				UserCartDetails userCartDetails = new UserCartDetails();

				userCartDetails.setUserDetails(userDetails);
				userCartDetails.setSku(sku);
				userCartDetails.setItemQuantity(itemQuantity);
				userCartDetails.setItemDescription(itemDetails.getItemDescription());
                userCartDetails.setItemCost(itemDetails.getItemCost());
				userCartDetails.setStatus('A');

				userCartRepository.save(userCartDetails);
			}else {
				throw new IllegalArgumentException("Item not Found!!");
			}

		} else {
			throw new IllegalArgumentException("User not Found");
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

}
