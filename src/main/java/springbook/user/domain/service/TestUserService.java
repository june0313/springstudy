package springbook.user.domain.service;

import springbook.user.domain.User;

import java.util.List;

/**
 * Created by wayne on 2015. 11. 15..
 */
public class TestUserService extends UserServiceImpl {
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

	static class TestUserServiceException extends RuntimeException {
	}
}
