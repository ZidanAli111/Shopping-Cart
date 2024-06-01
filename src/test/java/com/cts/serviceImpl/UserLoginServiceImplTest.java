package com.cts.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.exception.LoginException;
import com.cts.model.UserDetails;
import com.cts.repository.UserRepository;

public class UserLoginServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserLoginServiceImpl userLoginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticateUser_SuccessfulAuthentication() {
        // Arrange
        String username = "testUser";
        String password = "password";
        UserDetails userDetails = new UserDetails();
        userDetails.setUsername(username);
        userDetails.setPassword(password);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userDetails));

        // Act
        UserDetails authenticatedUser = userLoginService.authenticateUser(username, password);

        // Assert
        assertEquals(userDetails, authenticatedUser);
    }

    @Test
    void testAuthenticateUser_InvalidUsername() {
        // Arrange
        String username = "invalidUser";
        String password = "password";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(LoginException.class, () -> userLoginService.authenticateUser(username, password));
    }

    @Test
    void testAuthenticateUser_InvalidPassword() {
        // Arrange
        String username = "testUser";
        String password = "invalidPassword";
        UserDetails userDetails = new UserDetails();
        userDetails.setUsername(username);
        userDetails.setPassword("password"); // Correct password
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userDetails));

        // Act and Assert
        assertThrows(LoginException.class, () -> userLoginService.authenticateUser(username, password));
    }
}
