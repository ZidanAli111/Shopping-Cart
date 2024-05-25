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
	public ResponseEntity<UserLoginDTO> login(@RequestBody UserLoginDTO userLoginDTO) {
		int userId = userLoginDTO.getUserId();
		String username = userLoginDTO.getUsername();
		String password = userLoginDTO.getPassword();

		try {

			if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
				throw new LoginException("Invalid username or Password");
			}

			boolean isAuthenticated = loginService.authenticateUser(username, password);
			if (isAuthenticated) {
				return ResponseEntity.ok(userLoginDTO);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userLoginDTO);

			}

		} catch (LoginException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userLoginDTO);
		}
	}

	@GetMapping("{userId}")
	public ResponseEntity<UserDetails> getUserDetails(@PathVariable int userId) {
		return ResponseEntity.ok(this.loginService.getuserDetails(userId));
	}

}
