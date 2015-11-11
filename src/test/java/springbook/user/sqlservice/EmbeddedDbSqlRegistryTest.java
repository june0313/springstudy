package springbook.user.sqlservice;

import org.exolab.castor.mapping.xml.Sql;
import org.junit.After;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;

/**
 * Created by wayne on 2015. 11. 11..
 */
public class EmbeddedDbSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
	private EmbeddedDatabase db;

	@Override
	protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
		this.db = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.HSQL)
				.addScript("classpath:/schema.sql")
				.build();

		EmbeddedDbSqlRegistry embeddedDbSqlRegistry = new EmbeddedDbSqlRegistry();
		embeddedDbSqlRegistry.setDataSource(this.db);

		return embeddedDbSqlRegistry;
	}

	@After
	public void tearDown() throws Exception {
		this.db.shutdown();
	}

	@Test
	public void transactionalUpdate() throws Exception {
		checkFindResult("SQL1", "SQL2", "SQL3");

		Map<String, String> sqlmap = new HashMap<>();
		sqlmap.put("KEY1", "Modified1");
		sqlmap.put("KEY9999!@#$", "Modified9999");

		try {
			sqlRegistry.updateSql(sqlmap);
			fail();
		} catch (SqlUpdateFailureException e) {
		}

		checkFindResult("SQL1", "SQL2", "SQL3");
	}
}