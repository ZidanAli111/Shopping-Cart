package com.cts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cts.dto.UserLoginDTO;
import com.cts.exception.LoginException;
import com.cts.model.UserDetails;
import com.cts.service.UserLoginService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/login")
public class UserLoginController {

	@Autowired
	UserLoginService loginService;

	@PostMapping
	public ResponseEntity<UserDetails> login(@RequestBody UserLoginDTO userLoginDTO) throws LoginException {
		String username = userLoginDTO.getUsername();
		String password = userLoginDTO.getPassword();


			if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
				throw new LoginException("Invalid username or Password");
			}

			UserDetails userDetails = loginService.authenticateUser(username, password);
			if (userDetails!=null) {
				return ResponseEntity.ok(userDetails);
			} else {
				throw new LoginException("UNAUTORIZED");
			}

	}

	@GetMapping("{userId}")
	public ResponseEntity<UserDetails> getUserDetails(@PathVariable int userId) {
		return ResponseEntity.ok(this.loginService.getuserDetails(userId));
	}

}
