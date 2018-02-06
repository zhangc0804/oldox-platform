package com.oldox.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.oldox.platform.entity.User;
import com.oldox.platform.mapper.UserMapper;

@RestController()
public class MybatisController {
	
	@Autowired
	private UserMapper userMapper;
	
	@GetMapping("/user/{id}")
	public User queryUserById(@PathVariable(name="id") Long id){
		User user = userMapper.queryById(id);
		return user;
	}

}
