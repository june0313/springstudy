package springbook.user.domain.service;

import org.springframework.transaction.annotation.Transactional;
import springbook.user.domain.User;

import java.util.List;

/**
 * Created by Wayne on 2015. 10. 8..
 */
@Transactional
public interface UserService {
	void add(User user);

	void update(User user);

	void upgradeLevels() throws Exception;

	void deleteAll();

	@Transactional(readOnly = true)
	User get(String id);

	@Transactional(readOnly = true)
	List<User> getAll();
}
