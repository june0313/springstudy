package springbook.user.domain;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

/**
 * Created by Wayne on 2015. 9. 9..
 */
public class UserDaoTest {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
//		//		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
//		//		DaoFactory factory = new DaoFactory();
//		//
//		//		UserDao dao1 = factory.userDao();
//		//		UserDao dao2 = factory.userDao();
//
//		//		System.out.println(dao1);
//		//		System.out.println(dao2);
//
//		UserDao dao3 = context.getBean("userDao", UserDao.class);
//		User user1 = new User();
//		user1.setId("vvvvv");
//		user1.setName("vvvvv");
//		user1.setPassword("1234");
//		dao3.add(user1);
//
//		UserDao dao4 = context.getBean("userDao", UserDao.class);
//		User user2 = dao4.get("pepsi");
//
//		if (!user1.getName().equals(user2.getName())) {
//			System.out.println("테스트 실패 (네임)");
//		} else if (!user1.getPassword().equals(user2.getPassword())) {
//			System.out.printf("테스트 실패 (패스워드)");
//		} else {
//			System.out.println("테스트 성공");
//		}

	}


}
