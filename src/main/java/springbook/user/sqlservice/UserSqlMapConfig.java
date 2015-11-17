package springbook.user.sqlservice;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import springbook.user.domain.UserDao;

/**
 * Created by wayne on 2015. 11. 17..
 */
public class UserSqlMapConfig implements SqlMapConfig {
	@Override
	public Resource getSqlMapResource() {
		return new ClassPathResource("/salmap.xml", UserDao.class);
	}
}
