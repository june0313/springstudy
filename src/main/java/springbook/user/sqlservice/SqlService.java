package springbook.user.sqlservice;

/**
 * Created by wayne on 2015. 10. 27..
 */
public interface SqlService {
	String getSql(String key) throws SqlRetrievalFailureException;
}
