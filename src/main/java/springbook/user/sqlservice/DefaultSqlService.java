package springbook.user.sqlservice;

/**
 * Created by wayne on 2015. 11. 1..
 */
public class DefaultSqlService extends BaseSqlService {
	public DefaultSqlService() {
		setSqlReader(new JaxbXmlSqlReader());
		setSqlRegistry(new HashMapSqlRegistry());
	}
}
