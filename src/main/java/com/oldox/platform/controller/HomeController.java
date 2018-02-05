package com.oldox.platform.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@Value("${com.zyd.title3}")
	private String title;
	
	@Value("${com.zyd.type3}")
	private String type;
	
	@GetMapping("/")
	public String helloWorld(){
		
		System.out.println("title:"+title);
		System.out.println("type:"+type);
		
		return "hello world";
	}
	
}
