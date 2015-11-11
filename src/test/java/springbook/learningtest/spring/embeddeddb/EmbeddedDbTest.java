package springbook.learningtest.spring.embeddeddb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by wayne on 2015. 11. 9..
 */
public class EmbeddedDbTest {
	private EmbeddedDatabase db;
	private SimpleJdbcTemplate template;

	@Before
	public void setUp() throws Exception {
		this.db = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.HSQL)
				.addScript("classpath:/schema.sql")
				.addScript("classpath:/data.sql")
				.build();

		this.template = new SimpleJdbcTemplate(this.db);
	}

	@After
	public void tearDown() throws Exception {
		this.db.shutdown();
	}

	@Test
	public void initData() throws Exception {
		assertThat(this.template.queryForInt("SELECT count(*) from sqlmap"), is(2));

		List<Map<String, Object>> list = this.template.queryForList("SELECT * FROM sqlmap ORDER BY KEY_");
		assertThat((String)list.get(0).get("key_"), is("KEY1"));
		assertThat((String)list.get(0).get("sql_"), is("SQL1"));
		assertThat((String)list.get(1).get("key_"), is("KEY2"));
		assertThat((String)list.get(1).get("sql_"), is("SQL2"));
	}

	@Test
	public void testInsert() throws Exception {
		this.template.update("INSERT INTO sqlmap(key_, sql_) VALUES (?, ?)", "KEY3", "SQL3");

		assertThat(this.template.queryForInt("SELECT count(*) FROM sqlmap"), is(3));

	}
}
