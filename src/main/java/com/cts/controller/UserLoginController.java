package com.cts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dto.UserLoginDTO;
import com.cts.exception.LoginException;
import com.cts.service.UserLoginService;

@RestController
@RequestMapping("/api/login")
public class UserLoginController {

	@Autowired
	UserLoginService loginService;

	@PostMapping
	public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO) {

		String username = userLoginDTO.getUsername();
		String password = userLoginDTO.getPassword();

		try {

			if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
				throw new LoginException("Invalid username or Password");
			}

			boolean isAuthenticated = loginService.authenticateUser(username, password);
			if (isAuthenticated) {
				return ResponseEntity.ok("Login Successfull!");
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");

			}

		} catch (LoginException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
