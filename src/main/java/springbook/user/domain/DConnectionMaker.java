package springbook.user.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Wayne on 2015. 9. 9..
 */
public class DConnectionMaker implements ConnectionMaker {
	@Override public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost/MySpring", "root", null);
	}
}
