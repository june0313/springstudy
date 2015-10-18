package springbook.user.domain.service;

import org.junit.Before;
import org.junit.Test;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Wayne on 2015. 10. 2..
 */
public class UserTest {
	User user;

	@Before
	public void setUp() throws Exception {
		user = new User();
	}

	@Test
	public void testUpgradeLevel() throws Exception {
		Level[] levels = Level.values();
		for(Level level : levels) {
			if(level.nextLevel() == null) {
				continue;
			}

			user.setLevel(level);
			user.upgradeLevel();

			assertThat(user.getLevel(), is(level.nextLevel()));
		}
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotUpgradeLevel() throws Exception {
		Level[] levels = Level.values();
		for(Level level : levels) {
			if(level.nextLevel() != null) {
				continue;
			}

			user.setLevel(level);
			user.upgradeLevel();
		}
	}
}
