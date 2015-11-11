package springbook.user.sqlservice;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by wayne on 2015. 11. 11..
 */
public abstract class AbstractUpdatableSqlRegistryTest {
	private UpdatableSqlRegistry sqlRegistry;

	@Before
	public void setUp() throws Exception {
		sqlRegistry = createUpdatableSqlRegistry();
		sqlRegistry.registerSql("KEY1", "SQL1");
		sqlRegistry.registerSql("KEY2", "SQL2");
		sqlRegistry.registerSql("KEY3", "SQL3");
	}

	abstract protected UpdatableSqlRegistry createUpdatableSqlRegistry();

	protected void checkFindResult(String expected1, String expected2, String expected3) {
		assertThat(sqlRegistry.findSql("KEY1"), is(expected1));
		assertThat(sqlRegistry.findSql("KEY2"), is(expected2));
		assertThat(sqlRegistry.findSql("KEY3"), is(expected3));
	}

	@Test
	public void testFind() throws Exception {
		checkFindResult("SQL1", "SQL2", "SQL3");
	}

	@Test(expected = SqlNotFoundException.class)
	public void testUnknownKey() throws Exception {
		sqlRegistry.findSql("SQL9999!@#$");
	}

	@Test
	public void testUpdateSingle() throws Exception {
		sqlRegistry.updateSql("KEY2", "Modified2");
		checkFindResult("SQL1", "Modified2", "SQL3");
	}

	@Test
	public void testUpdateMulti() throws Exception {
		Map<String, String> sqlmap = new HashMap();
		sqlmap.put("KEY1", "Modified1");
		sqlmap.put("KEY3", "Modified3");

		sqlRegistry.updateSql(sqlmap);
		checkFindResult("Modified1", "SQL2", "Modified3");
	}

	@Test(expected = SqlUpdateFailureException.class)
	public void testUpdateWithNotExistingKey() throws Exception {
		sqlRegistry.updateSql("SQL9999!@#$", "Modified2");
	}
}
