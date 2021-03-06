package springbook.user.sqlservice;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Created by wayne on 2015. 11. 10..
 */
public class EmbeddedDbSqlRegistry implements UpdatableSqlRegistry {
	private SimpleJdbcTemplate jdbc;
	private TransactionTemplate transactionTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbc = new SimpleJdbcTemplate(dataSource);
		this.transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
	}

	@Override
	public void registerSql(String key, String sql) {
		this.jdbc.update("INSERT INTO sqlmap (key_, sql_) values(?, ?)", key, sql);
	}

	@Override
	public String findSql(String key) throws SqlNotFoundException {
		try {
			return this.jdbc.queryForObject("SELECT sql_ FROM sqlmap WHERE key_ = ?", String.class, key);
		} catch (EmptyResultDataAccessException e) {
			throw new SqlNotFoundException(key + "에 해당하는 SQL을 찾을 수 없습니다. e");
		}
	}

	@Override
	public void updateSql(String key, String sql) throws SqlUpdateFailureException {
		int affected = this.jdbc.update("UPDATE sqlmap SET sql_ = ? WHERE key_ = ?", sql, key);
		if (affected == 0) {
			throw new SqlUpdateFailureException(key + "에 해당하는 SQL을 찾을 수 없습니다.");
		}
	}

	@Override
	public void updateSql(final Map<String, String> sqlmap) throws SqlUpdateFailureException {

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				for (Map.Entry<String, String> entry : sqlmap.entrySet()) {
					updateSql(entry.getKey(), entry.getValue());
				}
			}
		});


	}
}
