package springbook.user.domain.service;

import springbook.user.domain.User;

/**
 * Created by Wayne on 2015. 10. 4..
 */
public interface UserLevelUpgradePolicy {
	boolean canUpgradeLevel(User user);
}

