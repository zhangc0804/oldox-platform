package com.oldox.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oldox.platform.entity.User;
import com.oldox.platform.mapper.UserMapper;
import com.oldox.platform.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User queryByUsername(String username) {
		return null;
	}

	@Override
	public User queryByUsernamePassword(String username, String password) {
		return null;
	}

}
