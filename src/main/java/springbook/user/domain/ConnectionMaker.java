package springbook.user.domain;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Wayne on 2015. 9. 9..
 */
public interface ConnectionMaker {
	Connection getConnection() throws ClassNotFoundException, SQLException;
}
