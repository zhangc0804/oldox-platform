import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyRealm extends AuthorizingRealm {
	
	private static Map<String, String> dataSources = new HashMap<>();
	
	static{
		dataSources.put("zhangsan", "123456");
		dataSources.put("lisi", "123456");
		dataSources.put("wangwu", "123456");
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		// Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        
        if(!dataSources.containsKey(username)){
        	throw new AccountException("Account["+username+"] is not exits.");
        }
        
        if(!dataSources.get(username).equals(new String(upToken.getPassword()))){
        	throw new AccountException("username/password is wrong.");
        }
        
        AuthenticationInfo info = new SimpleAuthenticationInfo(username, upToken.getPassword(), getName());
		return info;
	}

}
