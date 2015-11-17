package springbook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springbook.user.domain.UserDao;
import springbook.user.domain.service.DummyMailSender;
import springbook.user.domain.service.TestUserService;
import springbook.user.domain.service.UserService;
import springbook.user.sqlservice.EnableSqlService;
import springbook.user.sqlservice.SqlMapConfig;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by wayne on 2015. 11. 14..
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "springbook.user")
@EnableSqlService
@PropertySource("classpath:/database.properties")
public class AppContext implements SqlMapConfig{
	@Autowired
	UserDao userDao;

	@Resource
	Environment env;

	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		try {
			dataSource.setDriverClass((Class<? extends java.sql.Driver>) Class.forName(env.getProperty("db.driverClass")));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		dataSource.setUrl(env.getProperty("db.url"));
		dataSource.setUsername(env.getProperty("db.username"));
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(dataSource());
		return tm;
	}

	@Override
	public org.springframework.core.io.Resource getSqlMapResource() {
		return new ClassPathResource("/sqlmap.xml", UserDao.class);
	}

	@Configuration
	@Profile("production")
	public static class ProductionAppContext {
		@Bean
		public MailSender mailSender() {
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			mailSender.setHost("localhost");
			return mailSender;
		}
	}

	@Configuration
	@Profile("test")
	public static class TestAppContext {
		@Bean
		public UserService testUserService() {
			return new TestUserService();
		}

		@Bean
		public MailSender mailSender() {
			return new DummyMailSender();
		}
	}
}
