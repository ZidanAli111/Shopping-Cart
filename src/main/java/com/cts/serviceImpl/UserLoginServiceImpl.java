package com.cts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.model.UserDetails;
import com.cts.repository.UserRepository;
import com.cts.service.UserLoginService;

@Service
public class UserLoginServiceImpl implements UserLoginService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails authenticateUser(String username, String password) {
		// Find user by userName
		UserDetails user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Username not found!!"));

		// Validate password
		if (user.getPassword().equals(password)) {
			return user;
		} else {
			throw new RuntimeException("Invalid username or password");
		}
	}

	@Override
	public UserDetails getuserDetails(int userId) {
		// Find user by ID
		return userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
	}

}
