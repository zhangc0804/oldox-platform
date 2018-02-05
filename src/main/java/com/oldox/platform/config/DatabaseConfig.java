package com.oldox.platform.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;

@Configuration
public class DatabaseConfig implements EnvironmentAware {

	// 一些默认值
	private static final int SPRING_DATABASE_DEFAULT_INITIAL_SIZE = 1;
	private static final int SPRING_DATABASE_DEFAULT_MIN_IDLE = 1;
	private static final int SPRING_DATABASE_DEFAULT_MAX_ACTIVE = 20;

	private static final long SPRING_DATABASE_DEFAULT_MAX_WAIT = 60000;

	private static final long SPRING_DATABASE_DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 60000;

	private static final long SPRING_DATABASE_DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS = 300000;

	private static final String SPRING_DATABASE_DEFAULT_VALIDATION_QUERY = "SELECT 'x'";

	private static final boolean SPRING_DATABASE_DEFAULT_TEST_WHILE_IDLE = true;
	private static final boolean SPRING_DATABASE_DEFAULT_TEST_ON_BORROW = false;
	private static final boolean SPRING_DATABASE_DEFAULT_TEST_ON_RETURN = false;

	private static final boolean SPRING_DATABASE_DEFAULT_POOL_PREPARED_STATEMENTS = true;
	private static final int SPRING_DATABASE_DEFAULT_MAX_POOL_PREPARED_STATEMENT_PER_CONNECTION_SIZE = 20;

	private static final String SPRING_DATABASE_DEFAULT_FILTERS = "stat";

	// 获取properties配置文件中的配置
	private Environment environment;

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
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
		// druidDataSource.setFilters(environment.getProperty("spring.database.filters"));
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		return druidDataSource;
	}

}
