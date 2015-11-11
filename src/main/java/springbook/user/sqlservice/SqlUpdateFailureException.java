package springbook.user.sqlservice;

/**
 * Created by wayne on 2015. 11. 7..
 */
public class SqlUpdateFailureException extends RuntimeException {
	public SqlUpdateFailureException(String msg) {
		super(msg);
	}
}
