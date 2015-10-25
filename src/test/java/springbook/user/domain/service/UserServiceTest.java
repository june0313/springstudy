package springbook.user.domain.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.domain.UserDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static springbook.user.domain.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SLIVER;
import static springbook.user.domain.service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;

/**
 * Created by Wayne on 2015. 10. 1..
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest {

	static class TestUserService extends UserServiceImpl {
		private String id = "madnite1";

		@Override
		protected void upgradeLevel(User user) {
			if (user.getId().equals(this.id)) {
				throw new TestUserServiceException();
			}
			super.upgradeLevel(user);
		}

		@Override public List<User> getAll() {
			for (User user : super.getAll()) {
				super.update(user);
			}
			return null;
		}
	}

	static class TestUserServiceException extends RuntimeException {
	}

	static class MockMailSender implements MailSender {
		private List<String> requests = new ArrayList<String>();

		public List<String> getRequests() {
			return requests;
		}

		@Override public void send(SimpleMailMessage mailMessage) throws MailException {
			requests.add(mailMessage.getTo()[0]);
		}

		@Override public void send(SimpleMailMessage[] mailMessage) throws MailException {

		}
	}

	static class MockUserDao implements UserDao {
		private List<User> users;
		private List<User> updated = new ArrayList();

		public MockUserDao(List<User> users) {
			this.users = users;
		}

		public List<User> getUpdated() {
			return updated;
		}

		@Override public List<User> getAll() {
			return this.users;
		}

		@Override public void update(User user) {
			updated.add(user);
		}

		@Override public void add(User user) {
			throw new UnsupportedOperationException();
		}

		@Override public void deleteAll() {
			throw new UnsupportedOperationException();
		}

		@Override public User get(String id) {
			throw new UnsupportedOperationException();
		}

		@Override public int getCount() {
			throw new UnsupportedOperationException();
		}
	}

	@Autowired
	private UserService userService;

	@Autowired
	private UserService testUserService;

	@Autowired
	private BasicUserLevelUpgradePolicy basicUserLevelUpgradePolicy;

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserLevelUpgradePolicy userLevelUpgradePolicy;

	@Autowired
	private MailSender mailSender;

	@Autowired
	private ApplicationContext context;

	@Autowired
	private PlatformTransactionManager transactionManager;

	private List<User> users;

	@Before
	public void setUp() throws Exception {
		users = Arrays.asList(
			new User("bumjin", "parkbumjin", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SLIVER - 1, 0, "bumjin@naver.com"),
			new User("joytouch", "kangmyeongseong", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SLIVER, 0, "joytouch@gmail.com"),
			new User("erwins", "shin", "p3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD - 1, "erwins@naver.com"),
			new User("madnite1", "leesangho", "p4", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD, "madite1@coupang.com"),
			new User("green", "ohminkyu", "p5", Level.GOLD, 100, Integer.MAX_VALUE, "green@gmail.com")
		);
	}

	@Test
	public void testAdd() throws Exception {
		userDao.deleteAll();

		User userWithLevel = users.get(4); // GOLD Level
		User userWithoutLevel = users.get(0);
		userWithoutLevel.setLevel(null);

		userService.add(userWithLevel);
		userService.add(userWithoutLevel);

		User userWithLevelRead = userDao.get(userWithLevel.getId());
		User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

		assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
		assertThat(userWithoutLevelRead.getLevel(), is(Level.BASIC));
	}

	@Test
	@DirtiesContext
	public void testUpgradeLevels() throws Exception {
		UserServiceImpl userServiceImpl = new UserServiceImpl();

		MockUserDao mockUserDao = new MockUserDao(this.users);
		userServiceImpl.setUserDao(mockUserDao);

		userServiceImpl.setLevelUpgradePolicy(this.basicUserLevelUpgradePolicy);

		MockMailSender mockMailSender = new MockMailSender();
		userServiceImpl.setMailSender(mockMailSender);

		userServiceImpl.upgradeLevels();

		List<User> updated = mockUserDao.getUpdated();
		assertThat(updated.size(), is(2));
		checkUserAndLevel(updated.get(0), "joytouch", Level.SILVER);
		checkUserAndLevel(updated.get(1), "madnite1", Level.GOLD);

		List<String> requests = mockMailSender.getRequests();
		assertThat(requests.size(), is(2));
		assertThat(requests.get(0), is(users.get(1).getEmail()));
		assertThat(requests.get(1), is(users.get(3).getEmail()));
	}

	@Test
	public void bean() throws Exception {
		assertThat(this.userService, is(notNullValue()));
	}

	@Test
	public void testUpgradeAllOrNothing() throws Exception {
		userDao.deleteAll();
		for (User user : users) {
			userDao.add(user);
		}

		try {
			this.testUserService.upgradeLevels();
			fail("TestUserServiceException expected");
		} catch (TestUserServiceException e) {

		}

		checkLevelUpgraded(users.get(1), false);
	}

	@Test
	public void testMockUpgradeLevels() throws Exception {
		UserServiceImpl userServiceImpl = new UserServiceImpl();

		UserDao mockUserDao = mock(UserDao.class);
		when(mockUserDao.getAll()).thenReturn(this.users);
		userServiceImpl.setUserDao(mockUserDao);

		MailSender mockMailSender = mock(MailSender.class);
		userServiceImpl.setMailSender(mockMailSender);

		userServiceImpl.setLevelUpgradePolicy(this.basicUserLevelUpgradePolicy);

		userServiceImpl.upgradeLevels();

		verify(mockUserDao, times(2)).update(any(User.class));
		verify(mockUserDao).update(users.get(1));
		assertThat(users.get(1).getLevel(), is(Level.SILVER));
		verify(mockUserDao).update(users.get(3));
		assertThat(users.get(3).getLevel(), is(Level.GOLD));

		ArgumentCaptor<SimpleMailMessage> mailMessageArg = ArgumentCaptor.forClass(SimpleMailMessage.class);
		verify(mockMailSender, times(2)).send(mailMessageArg.capture());
		List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
		assertThat(mailMessages.get(0).getTo()[0], is(users.get(1).getEmail()));
		assertThat(mailMessages.get(1).getTo()[0], is(users.get(3).getEmail()));
	}

	@Test
	public void testAdvisorAutoProxyCreator() throws Exception {
		assertThat(this.testUserService, instanceOf(java.lang.reflect.Proxy.class));
	}

	@Test(expected = TransientDataAccessException.class)
	public void testReadOnlyTransactionAttribute() {
		testUserService.getAll();
	}

	@Test
	public void testTransactionSync() throws Exception {
		userDao.deleteAll();
		assertThat(userDao.getCount(), is(0));

		DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(transactionDefinition);

		userService.add(users.get(0));
		userService.add(users.get(1));
		assertThat(userDao.getCount(), is(2));

//		transactionManager.rollback(status);
		transactionManager.commit(status);
		assertThat(userDao.getCount(), is(2));
	}

	private void checkLevelUpgraded(User user, boolean upgraded) {
		User userUpdate = userDao.get(user.getId());
		if (upgraded) {
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		} else {
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
		}
	}

	private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
		assertThat(updated.getId(), is(expectedId));
		assertThat(updated.getLevel(), is(expectedLevel));
	}
}