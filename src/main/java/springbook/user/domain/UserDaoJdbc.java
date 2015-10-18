package springbook.user.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Wayne on 2015. 9. 9..
 */
public class UserDaoJdbc implements UserDao {
	private JdbcTemplate jdbcTemplate;

	private RowMapper<User> userMapper = new RowMapper<User>() {
		@Override public User mapRow(ResultSet rs, int rowNum) throws SQLException {
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

	@Override public void add(final User user) {
		this.jdbcTemplate.update("insert into users(id, name, password, level, login, recommend, email) values(?,?,?,?,?,?,?)",
			user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail());
	}

	@Override public void deleteAll() {
		this.jdbcTemplate.update("delete from users");
	}

	@Override public User get(String id) {
		return this.jdbcTemplate.queryForObject("select * from users where id = ?",
			new Object[] { id }, this.userMapper);
	}

	@Override public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id", this.userMapper);
	}

	@Override public int getCount() {
		return this.jdbcTemplate.queryForInt("select count(*) from users");
	}

	@Override public void update(User user) {
		this.jdbcTemplate.update("update users set name=?, password=?, level=?, login=?, recommend=?, email=? where id=?",
			user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail(), user.getId());
	}
}
