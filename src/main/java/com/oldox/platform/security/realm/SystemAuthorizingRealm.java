package com.oldox.platform.security.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.oldox.platform.entity.User;
import com.oldox.platform.mapper.UserMapper;

public class SystemAuthorizingRealm extends AuthorizingRealm {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken uptoken = (UsernamePasswordToken)token;
		User user = userMapper.queryByUsername(uptoken.getUsername());
		if(user==null){
			return null;
		}
			
		return new SimpleAuthenticationInfo(uptoken.getUsername(), uptoken.getPassword(), getName());
	}

}
