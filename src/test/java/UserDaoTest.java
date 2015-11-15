import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.config.AppContext;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.domain.UserDao;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Wayne on 2015. 9. 13..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {AppContext.class})
public class UserDaoTest {
	@Autowired
	private AppContext context;

	@Autowired
	private UserDao dao;

	@Autowired
	private DataSource dataSource;

	private User user1;
	private User user2;
	private User user3;

	@Before
	public void setUp() throws Exception {
		System.out.println(this.context);
		System.out.println(this);

		this.user1 = new User("june0313", "youngjun", "spring1", Level.BASIC, 1, 0, "june0313@gmail.com");
		this.user2 = new User("qhxhdwns", "sunyoung", "spring2", Level.SILVER, 55, 10, "qhxhdwns@coupang.com");
		this.user3 = new User("whhaa", "jiheyon", "spring3", Level.GOLD, 100, 40, "whhaa@naver.com");
	}

	@Test
	public void testAddAndGet() throws Exception {

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		dao.add(user1);
		dao.add(user2);
		assertThat(dao.getCount(), is(2));

		User userget1 = dao.get(user1.getId());
		checkSameUser(userget1, user1);

		User userget2 = dao.get(user2.getId());
		checkSameUser(userget2, user2);
	}

	@Test
	public void testGetCount() throws Exception {

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		dao.add(user1);
		assertThat(dao.getCount(), is(1));

		dao.add(user2);
		assertThat(dao.getCount(), is(2));

		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void testUserFailuare() throws Exception {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		dao.get("unknown_id");
	}

	@Test
	public void testGetAll() throws Exception {
		dao.deleteAll();

		List<User> users0 = dao.getAll();
		assertThat(users0.size(), is(0));

		dao.add(user1);
		List<User> users1 = dao.getAll();
		assertThat(users1.size(), is(1));
		checkSameUser(user1, users1.get(0));

		dao.add(user2);
		List<User> users2 = dao.getAll();
		assertThat(users2.size(), is(2));
		checkSameUser(user1, users2.get(0));
		checkSameUser(user2, users2.get(1));

		dao.add(user3);
		List<User> users3 = dao.getAll();
		assertThat(users3.size(), is(3));
		checkSameUser(user1, users3.get(0));
		checkSameUser(user2, users3.get(1));
		checkSameUser(user3, users3.get(2));
	}

	@Test(expected = DuplicateKeyException.class)
	public void testDuplicateKey() throws Exception {
		dao.deleteAll();

		dao.add(user1);
		dao.add(user1);
	}

	@Test
	public void testSqlExceptionTranslate() {
		dao.deleteAll();

		try {
			dao.add(user1);
			dao.add(user1);
		} catch (DuplicateKeyException ex) {
			SQLException sqlEx = (SQLException) ex.getRootCause();
			SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);

			assertThat(set.translate(null, null, sqlEx), is(DataAccessException.class));
		}
	}

	@Test
	public void testUpdate() throws Exception {
		dao.deleteAll();
		dao.add(user1);
		dao.add(user2);

		user1.setName("Wayne");
		user1.setPassword("springno6");
		user1.setLevel(Level.GOLD);
		user1.setLogin(1000);
		user1.setRecommend(999);
		dao.update(user1);

		User user1update = dao.get(user1.getId());
		checkSameUser(user1, user1update);

		User user2same = dao.get(user2.getId());
		checkSameUser(user2, user2same);
	}

	private void checkSameUser(User user1, User user2) {
		assertThat(user1.getId(), is(user2.getId()));
		assertThat(user1.getName(), is(user2.getName()));
		assertThat(user1.getPassword(), is(user2.getPassword()));
		assertThat(user1.getLevel(), is(user2.getLevel()));
		assertThat(user1.getLogin(), is(user2.getLogin()));
		assertThat(user1.getRecommend(), is(user2.getRecommend()));
	}
}
