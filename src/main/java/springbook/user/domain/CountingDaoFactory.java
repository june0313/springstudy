//package springbook.user.domain;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.activation.DataSource;
//
///**
// * Created by Wayne on 2015. 9. 10..
// */
//@Configuration
//public class CountingDaoFactory {
//	@Bean
//	public UserDao userDao() {
//		UserDao userDao = new UserDao();
//		userDao.setDataSource(dataSource());
//		return userDao;
//	}
//
//	@Bean
//	public DataSource dataSource() {
//		return new CountingConnectionMaker(realConnectionMaker());
//	}
//
//	@Bean
//	public ConnectionMaker realConnectionMaker() {
//		return new DConnectionMaker();
//	}
//}
