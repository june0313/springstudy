package springbook.user.domain;

import java.util.List;

/**
 * Created by Wayne on 2015. 9. 24..
 */
public interface UserDao {
	void add(User user);

	void deleteAll();

	User get(String id);

	List<User> getAll();

	int getCount();

	void update(User user);
}
