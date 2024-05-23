package com.cts.service;

import com.cts.model.UserDetails;

public interface UserLoginService {
   
	boolean authenticateUser(String username,String password);
	
	UserDetails getuserDetails(int userId);
}
