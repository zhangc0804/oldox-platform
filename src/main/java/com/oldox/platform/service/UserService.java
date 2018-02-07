package com.oldox.platform.service;

import com.oldox.platform.entity.User;

public interface UserService {
	
	public User queryByUsername(String username);
	
	public User queryByUsernamePassword(String username,String password);

}
