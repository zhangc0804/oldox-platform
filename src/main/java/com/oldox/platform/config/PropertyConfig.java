package com.oldox.platform.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.oldox.platform.entity.MyProperty;

@Configuration
@ConfigurationProperties(prefix="com.zyd")
public class PropertyConfig implements EnvironmentAware{
	
	private Environment environment;
	
	private Map<String, String> login = new HashMap<String, String>();
	private List<String> urls = new ArrayList<>();

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	public Map<String, String> getLogin() {
		return login;
	}

	public void setLogin(Map<String, String> login) {
		this.login = login;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	@Bean
	public MyProperty myProperty(){
		MyProperty myProperty = new MyProperty();
		
		myProperty.setTitle(environment.getProperty("com.zyd.type2"));
		myProperty.setType(environment.getProperty("com.zyd.title2"));
		
		System.out.println(myProperty);
		System.out.println(login);
		System.out.println(urls);
		
		return myProperty;
	}

}
