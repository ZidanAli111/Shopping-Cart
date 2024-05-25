package com.cts.service;

import com.cts.model.UserDetails;

public interface UserLoginService {
   
	UserDetails authenticateUser(String username,String password);
	
	UserDetails getuserDetails(int userId);
}
