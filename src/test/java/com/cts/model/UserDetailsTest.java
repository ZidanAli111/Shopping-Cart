package com.cts.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.app.model.UserDetails;

public class UserDetailsTest {

	@Test
	public void testGetterAndSetters() {
		UserDetails userDetails=new UserDetails();
	
		userDetails.setUsername("zidan@gmail.com");
        userDetails.setPassword("Zid1234@");
        
        
        assertEquals("zidan@gmail.com", userDetails.getUsername());
        assertEquals("Zid1234@", userDetails.getPassword());
	}
	
}
