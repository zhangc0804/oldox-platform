package com.oldox.platform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping("/")
	public String helloWorld(){
		System.out.println("#####");
		return "hello world";
	}

}
