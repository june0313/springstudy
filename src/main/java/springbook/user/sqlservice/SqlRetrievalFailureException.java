package springbook.user.sqlservice;

/**
 * Created by wayne on 2015. 10. 27..
 */
public class SqlRetrievalFailureException extends RuntimeException {
	public SqlRetrievalFailureException(String message) {
		super(message);
	}

	public SqlRetrievalFailureException(String message, Throwable cause) {
		super(message, cause);
	}
}
