package springbook.user.domain;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

/**
 * Created by Wayne on 2015. 9. 10..
 */
public class UserDaoConnectionCountingTest {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

		UserDao dao = context.getBean("userDao", UserDaoJdbc.class);

		dao.get("wayne");
		dao.get("wayne");
		dao.get("wayne");
		dao.get("wayne");
		dao.get("wayne");
		dao.get("wayne");
		dao.get("wayne");
		dao.get("wayne");
		dao.get("wayne");
		dao.get("wayne");

		ConnectionMaker ccm = context.getBean("connectionMaker", ConnectionMaker.class);
//		System.out.println("Connection counter : " + ccm.getCounter());
	}
}
