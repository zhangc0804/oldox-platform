package com.oldox.platform.config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallFilter;

@Configuration
public class DatabaseConfig implements EnvironmentAware {

	// 一些默认值
	private static final int SPRING_DATABASE_DEFAULT_INITIAL_SIZE = 1;
	private static final int SPRING_DATABASE_DEFAULT_MIN_IDLE = 1;
	private static final int SPRING_DATABASE_DEFAULT_MAX_ACTIVE = 20;

	private static final long SPRING_DATABASE_DEFAULT_MAX_WAIT = 60000L;

	private static final long SPRING_DATABASE_DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 60000L;

	private static final long SPRING_DATABASE_DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS = 300000L;

	private static final String SPRING_DATABASE_DEFAULT_VALIDATION_QUERY = "SELECT 'x'";

	private static final boolean SPRING_DATABASE_DEFAULT_TEST_WHILE_IDLE = true;
	private static final boolean SPRING_DATABASE_DEFAULT_TEST_ON_BORROW = false;
	private static final boolean SPRING_DATABASE_DEFAULT_TEST_ON_RETURN = false;

	private static final boolean SPRING_DATABASE_DEFAULT_POOL_PREPARED_STATEMENTS = true;
	private static final int SPRING_DATABASE_DEFAULT_MAX_POOL_PREPARED_STATEMENT_PER_CONNECTION_SIZE = 20;

	private static final String SPRING_DATABASE_DEFAULT_FILTERS = "stat";

	private static final boolean SPRING_DATABASE_DEFAULT_MERGE_SQL = true;
	private static final long SPRING_DATABASE_DEFAULT_SHOW_SQL_MILLIS = 3000L;
	private static final boolean SPRING_DATABASE_DEFAULT_LOG_SHOW_SQL = true;

	// 获取properties配置文件中的配置
	private Environment environment;

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Bean
	public StatFilter statFilter() {
		StatFilter statFilter = new StatFilter();
		// SQL合并配置
		statFilter.setMergeSql(environment.getProperty("spring.database.mergeSql", Boolean.class,
				DatabaseConfig.SPRING_DATABASE_DEFAULT_MERGE_SQL));
		// 慢SQL记录
		// slowSqlMillis用来配置SQL慢的标准，执行时间超过slowSqlMillis的就是慢
		statFilter.setSlowSqlMillis(environment.getProperty("spring.database.slowSqlMillis", Long.class,
				DatabaseConfig.SPRING_DATABASE_DEFAULT_SHOW_SQL_MILLIS));
		// 通过日志输出执行慢的SQL
		statFilter.setLogSlowSql(environment.getProperty("spring.database.logSlowSql", Boolean.class,
				DatabaseConfig.SPRING_DATABASE_DEFAULT_LOG_SHOW_SQL));
		return statFilter;
	}
	
	@Bean
	public WallFilter wallFilter(){
		WallFilter wallFilter = new WallFilter();
		
		//刚开始引入WallFilter的时候，把logViolation设置为true，而throwException设置为false。
		//就可以观察是否存在违规的情况，同时不影响业务运行。
		
		//对被认为是攻击的SQL进行LOG.error输出
		wallFilter.setLogViolation(true);
		//对被认为是攻击的SQL抛出SQLExcepton
		wallFilter.setThrowException(false);
		return wallFilter;
	}

	@Bean(initMethod = "init", destroyMethod = "close")
	public DataSource dataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		// 基本属性 url、user、password
		// druidDataSource.setDriverClassName(environment.getProperty("spring.database.driverClassName"));
		druidDataSource.setUrl(environment.getProperty("spring.database.url"));
		druidDataSource.setUsername(environment.getProperty("spring.database.username"));
		druidDataSource.setPassword(environment.getProperty("spring.database.password"));
		// 配置初始化大小、最小、最大
		druidDataSource.setInitialSize(environment.getProperty("spring.database.initialSize", Integer.class,
				DatabaseConfig.SPRING_DATABASE_DEFAULT_INITIAL_SIZE));
		druidDataSource.setMinIdle(environment.getProperty("spring.database.minIdle", Integer.class,
				DatabaseConfig.SPRING_DATABASE_DEFAULT_MIN_IDLE));
		druidDataSource.setMaxActive(environment.getProperty("spring.database.maxActive", Integer.class,
				DatabaseConfig.SPRING_DATABASE_DEFAULT_MAX_ACTIVE));
		// 配置获取连接等待超时的时间
		druidDataSource.setMaxWait(environment.getProperty("spring.database.maxWait", Long.class,
				DatabaseConfig.SPRING_DATABASE_DEFAULT_MAX_WAIT));
		// 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
		druidDataSource.setTimeBetweenEvictionRunsMillis(
				environment.getProperty("spring.database.timeBetweenEvictionRunsMillis", Long.class,
						DatabaseConfig.SPRING_DATABASE_DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS));
		// 配置一个连接在池中最小生存的时间，单位是毫秒
		druidDataSource
				.setMinEvictableIdleTimeMillis(environment.getProperty("spring.database.minEvictableIdleTimeMillis",
						Long.class, DatabaseConfig.SPRING_DATABASE_DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS));

		druidDataSource.setValidationQuery(environment.getProperty("spring.database.validationQuery",
				DatabaseConfig.SPRING_DATABASE_DEFAULT_VALIDATION_QUERY));
		druidDataSource.setTestWhileIdle(environment.getProperty("spring.database.testWhileIdle", Boolean.class,
				DatabaseConfig.SPRING_DATABASE_DEFAULT_TEST_WHILE_IDLE));
		druidDataSource.setTestOnBorrow(environment.getProperty("spring.database.testOnBorrow", Boolean.class,
				DatabaseConfig.SPRING_DATABASE_DEFAULT_TEST_ON_BORROW));
		druidDataSource.setTestOnReturn(environment.getProperty("spring.database.testOnReturn", Boolean.class,
				DatabaseConfig.SPRING_DATABASE_DEFAULT_TEST_ON_RETURN));
		// 打开PSCache，并且指定每个连接上PSCache的大小
		druidDataSource.setPoolPreparedStatements(environment.getProperty("spring.database.poolPreparedStatements",
				Boolean.class, DatabaseConfig.SPRING_DATABASE_DEFAULT_POOL_PREPARED_STATEMENTS));
		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(
				environment.getProperty("spring.database.maxPoolPreparedStatementPerConnectionSize", Integer.class,
						DatabaseConfig.SPRING_DATABASE_DEFAULT_MAX_POOL_PREPARED_STATEMENT_PER_CONNECTION_SIZE));
		// 配置监控统计拦截的filters
		// try {
		// druidDataSource.setFilters(environment.getProperty("spring.database.filters",DatabaseConfig.SPRING_DATABASE_DEFAULT_FILTERS));
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		
		//filters和proxyFilters属性是组合关系的，不是替换的
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(wallFilter());
		filters.add(statFilter());
		druidDataSource.setProxyFilters(filters);

		return druidDataSource;
	}

	@Bean
	public ServletRegistrationBean druidStatViewServlet() {
		ServletRegistrationBean druidStatViewServlet = new ServletRegistrationBean();

		druidStatViewServlet.setName("DruidStatView");
		druidStatViewServlet.setServlet(new StatViewServlet());

		List<String> urlMappings = new ArrayList<String>();
		urlMappings.add("/druid/*");
		druidStatViewServlet.setUrlMappings(urlMappings);

		Map<String, String> initParameters = new HashMap<String, String>();
		// 允许清空统计数据
		initParameters.put("resetEnable", "true");
		// 用户名
		// initParameters.put("loginUsername", "root");
		// 密码
		// initParameters.put("loginPassword", "123456");
		// 访问控制，规则如下：
		// deny优先于allow，如果在deny列表中，就算在allow列表中，也会被拒绝。
		// 如果allow没有配置或者为空，则允许所有访问
		// 另外需要注意，由于匹配规则不支持IPV6，配置了allow或者deny之后，会导致IPV6无法访问。
		// initParameters.put("allow", "127.0.0.1");
		initParameters.put("deny", "127.0.0.1");
		druidStatViewServlet.setInitParameters(initParameters);

		return druidStatViewServlet;
	}
	
	@Bean
	public FilterRegistrationBean druidWebStatFilter(){
		FilterRegistrationBean druidWebStatFilter = new FilterRegistrationBean();
		druidWebStatFilter.setFilter(new WebStatFilter());
		druidWebStatFilter.setName("DruidWebStatFilter");
		
		List<String> urls = new ArrayList<String>();
		urls.add("/*");
		druidWebStatFilter.setUrlPatterns(urls);
		
		Map<String, String> initParameters = new HashMap<String, String>();
		//排除一些不必要的url，比如*.js,/jslib/*等等
		initParameters.put("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		//缺省sessionStatMaxCount是1000个。你可以按需要进行配置
		initParameters.put("sessionStatMaxCount","1000");
		//你可以关闭session统计功能
		initParameters.put("sessionStatEnable","true");
		//可以配置principalSessionName，使得druid能够知道当前的session的用户是谁
		//根据需要，把其中的xxx.user修改为你user信息保存在session中的sessionName
		//注意：如果你session中保存的是非string类型的对象，需要重载toString方法。
//		initParameters.put("principalSessionName","xxx.user");
		//如果你的user信息保存在cookie中，你可以配置principalCookieName，使得druid知道当前的user是谁
		//根据需要，把其中的xxx.user修改为你user信息保存在cookie中的cookieName
//		initParameters.put("principalCookieName","xxx.user");
		//配置profileEnable能够监控单个url调用的sql列表。
		initParameters.put("profileEnable","true");
		druidWebStatFilter.setInitParameters(initParameters );
		return druidWebStatFilter;
	}

}
