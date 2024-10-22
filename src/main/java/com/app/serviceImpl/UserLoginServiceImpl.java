package com.app.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.UserDetails;
import com.app.repository.UserRepository;
import com.app.service.UserLoginService;

@Service
public class UserLoginServiceImpl implements UserLoginService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean authenticateUser(String username, String password) {
		Optional<UserDetails> userOptional = Optional.ofNullable(userRepository.findByUsername(username)
				.orElseThrow(()->new RuntimeException("Username not found!!")));
		if(userOptional.isPresent()) {
			UserDetails user=userOptional.get();
			if(user.getPassword().equals(password)) {
				return true;
			}else {
				throw new RuntimeException("Invalid username or password");
			}
		}else {
			throw new RuntimeException("Invalid username or password");
		}
		

	}

}
