package com.oldox.platform.config;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oldox.platform.security.realm.SystemAuthorizingRealm;

@Configuration
public class ShiroConfig {
	
	@Bean
	public Realm realm(){
		return new SystemAuthorizingRealm();
	}
	
	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
	    DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
	    
	    // logged in users with the 'admin' role
//	    chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");
	    
	    // logged in users with the 'document:read' permission
//	    chainDefinition.addPathDefinition("/docs/**", "authc, perms[document:read]");
	    
	    // all other paths require a logged in user
//	    chainDefinition.addPathDefinition("/**", "authc");
	    
	    // all paths are managed via annotations
	    chainDefinition.addPathDefinition("/**", "anon");
	    return chainDefinition;
	}
	
//	@Bean
//	protected CacheManager cacheManager() {
//	    return new MemoryConstrainedCacheManager();
//	}

}
