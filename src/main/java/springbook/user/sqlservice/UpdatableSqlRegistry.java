package springbook.user.sqlservice;

import java.util.Map;

/**
 * Created by wayne on 2015. 11. 7..
 */
public interface UpdatableSqlRegistry extends SqlRegistry {
	public void updateSql(String key, String sql) throws SqlUpdateFailureException;

	public void updateSql(Map<String, String> sqlmap) throws SqlUpdateFailureException;
}
