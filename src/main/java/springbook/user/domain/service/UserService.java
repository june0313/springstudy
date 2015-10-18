package springbook.user.domain.service;

import springbook.user.domain.User;

/**
 * Created by Wayne on 2015. 10. 8..
 */
public interface UserService {
	void upgradeLevels() throws Exception;

	void add(User user);
}
