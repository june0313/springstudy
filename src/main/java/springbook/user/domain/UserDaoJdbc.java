package springbook.user.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Wayne on 2015. 9. 9..
 */
public class UserDaoJdbc implements UserDao {
	private JdbcTemplate jdbcTemplate;
	private Map<String, String> sqlMap;

	private RowMapper<User> userMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setLevel(Level.valueOf(rs.getInt("level")));
			user.setLogin(rs.getInt("login"));
			user.setRecommend(rs.getInt("recommend"));
			user.setEmail(rs.getString("email"));
			return user;
		}
	};

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}

	@Override
	public void add(final User user) {
		this.jdbcTemplate.update(this.sqlMap.get("add"),
				user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(),
				user.getLogin(), user.getRecommend(), user.getEmail());
	}

	@Override
	public void deleteAll() {
		this.jdbcTemplate.update(this.sqlMap.get("deleteAll"));
	}

	@Override
	public User get(String id) {
		return this.jdbcTemplate.queryForObject(sqlMap.get("get"),
				new Object[]{id}, this.userMapper);
	}

	@Override
	public List<User> getAll() {
		return this.jdbcTemplate.query(sqlMap.get("getAll"), this.userMapper);
	}

	@Override
	public int getCount() {
		return this.jdbcTemplate.queryForInt(sqlMap.get("getCount"));
	}

	@Override
	public void update(User user) {
		this.jdbcTemplate.update(sqlMap.get("update"),
				user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail(), user.getId());
	}
}
