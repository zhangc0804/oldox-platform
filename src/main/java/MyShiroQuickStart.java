import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyShiroQuickStart {
	
	private static final Logger logger = LoggerFactory.getLogger(MyShiroQuickStart.class);

	public static void main(String[] args) {
		
		Factory<SecurityManager> facotry = new IniSecurityManagerFactory("classpath:shiro.ini");
		SecurityManager securityManager = facotry.getInstance();
		
		SecurityUtils.setSecurityManager(securityManager);
		
		Subject subject = SecurityUtils.getSubject();
		

		Session session = subject.getSession();
		session.setAttribute("key1", "value1");
		String value1 = (String)session.getAttribute("key1");
		if("value1".equals(value1)){
			logger.info("查找到正确的值："+value1);
		}
		
		if(!subject.isAuthenticated()){
			UsernamePasswordToken token = new UsernamePasswordToken("root", "secret");
			try {
				subject.login(token);
			} catch (UnknownAccountException uae) {
				logger.info("用户不存在");
				System.exit(0);
			} catch (IncorrectCredentialsException lce) {
				logger.info("用户密码错误");
				System.exit(0);
			} catch (AuthenticationException ae) {
				logger.info("用户验证失败");
				System.exit(0);
			}
			
			logger.info("用户["+subject.getPrincipal()+"]登录成功！");
			logger.info("用户已经登录?"+subject.isAuthenticated());
			
			if(subject.hasRole("admin")){
				logger.info("用户["+subject.getPrincipal()+"]具有[admin]的角色");
			}else{
				logger.info("用户["+subject.getPrincipal()+"]没有[admin]的角色");
			}
			
			if(subject.isPermitted("lightsaber:*")){
				logger.info("用户["+subject.getPrincipal()+"]具有[lightsaber:*]的权限");
			}else{
				logger.info("用户["+subject.getPrincipal()+"]没有[lightsaber:*]的权限");
			}
			
			subject.logout();
			logger.info("用户已经退出登录！");
			logger.info("用户已经登录?"+subject.isAuthenticated());
			
			System.exit(0);
		}

	}

}
