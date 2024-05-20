package com.app.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.model.ItemDetails;
import com.app.model.UserCartDetails;
import com.app.model.UserDetails;
import com.app.repository.ItemRepository;
import com.app.repository.UserCartRepository;
import com.app.repository.UserRepository;
import com.app.service.UserCartService;

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

				UserCartDetails userCartDetails = new UserCartDetails();

				userCartDetails.setUserId(userId);
				userCartDetails.setSku(sku);
				userCartDetails.setItemQuantity(itemQuantity);
				userCartDetails.setItemDescription(itemDetails.getItemDescription());
				userCartDetails.setItemCost(itemDetails.getItemCost());
				userCartDetails.setStatus('A');

				userCartRepository.save(userCartDetails);
			} else {
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
			UserCartDetails optionalExistingUserCartDetails = userCartRepository
					.findByUserIdAndSku(userId, sku);

			if (optionalExistingUserCartDetails!=null){
				UserCartDetails existingUserCartDetails = optionalExistingUserCartDetails;
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
