package springbook.user.sqlservice;

/**
 * Created by wayne on 2015. 10. 30..
 */
public interface SqlRegistry {
	void registerSql(String key, String sql);

	String findSql(String key) throws SqlNotFoundException;
}
