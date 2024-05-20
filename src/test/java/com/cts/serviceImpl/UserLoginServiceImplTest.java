package com.cts.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.model.UserDetails;
import com.app.repository.UserRepository;
import com.app.serviceImpl.UserLoginServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserLoginServiceImplTest {

	@Mock
	private UserRepository userRepositoryMock;

	@InjectMocks
	private UserLoginServiceImpl userLoginService;

	@Test
	public void testAuthenticateUser_CorrectCredentials() {
		// Arrange
		String username = "testUser";
		String password = "password123";
		UserDetails userDetails = new UserDetails();
		userDetails.setUsername(username);
		userDetails.setPassword(password);
		when(userRepositoryMock.findByUsername(username)).thenReturn(Optional.of(userDetails));

		// Act
		boolean result = userLoginService.authenticateUser(username, password);

		// Assert
		assertTrue(result);
	}

	@Test
	public void testAuthenticateUser_IncorrectPassword() {
		// Arrange
		String username = "testUser";
		String password = "wrongPassword";
		UserDetails userDetails = new UserDetails();
		userDetails.setUsername(username);
		userDetails.setPassword("password123");
		when(userRepositoryMock.findByUsername(username)).thenReturn(Optional.of(userDetails));

		// Act
		boolean result = userLoginService.authenticateUser(username, password);

		// Assert
		assertFalse(result);
	}


}