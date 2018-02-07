package com.oldox.platform.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oldox.platform.common.http.ResponseMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("用户登录服务")
@RestController
@RequestMapping("/sys/login")
public class LoginRestController {
	
	@ApiOperation("登录")
	@GetMapping
	public ResponseMessage login(@RequestParam String username,@RequestParam String password){
		Subject subject = SecurityUtils.getSubject();
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);;
			subject.login(token);
		} catch (AuthenticationException e) {
			return new ResponseMessage(false, "500", "登录失败：用户名或密码错误");
		}
		return new ResponseMessage(true, "200", "登录成功！");
	}

}
