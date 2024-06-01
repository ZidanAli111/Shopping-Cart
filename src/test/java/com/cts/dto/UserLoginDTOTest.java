package com.cts.dto;




import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class UserLoginDTOTest {
	 
    @Test
    public void testConstructorAndGetters() {
        // Arrange
    	int userId=1;
        String username = "zidan@gmail.com";
        String password = "zid1234@";
 
        // Act
        UserLoginDTO userLoginDTO = new UserLoginDTO(userId,username, password);
 
        // Assert
        assertNotNull(userLoginDTO);
        assertEquals(username, userLoginDTO.getUsername());
        assertEquals(password, userLoginDTO.getPassword());
    }
 
    @Test
    public void testSetters() {
        // Arrange
        String newUsername = "zidan@gmail.com";
        String newPassword = "zid1234@";
        UserLoginDTO userLoginDTO = new UserLoginDTO();
 
        // Act
        userLoginDTO.setUsername(newUsername);
        userLoginDTO.setPassword(newPassword);
 
        // Assert
        assertEquals(newUsername, userLoginDTO.getUsername());
        assertEquals(newPassword, userLoginDTO.getPassword());
    }
    
    @Test
    public void testEmptyConstructor() {
        // Arrange & Act
        UserLoginDTO userLoginDTO = new UserLoginDTO();
 
        // Assert
        assertNotNull(userLoginDTO);
        assertNull(userLoginDTO.getUsername());
        assertNull(userLoginDTO.getPassword());
    }
}