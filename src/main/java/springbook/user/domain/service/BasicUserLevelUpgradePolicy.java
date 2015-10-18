package springbook.user.domain.service;

import springbook.user.domain.Level;
import springbook.user.domain.User;

import static springbook.user.domain.service.UserServiceImpl.*;

/**
 * Created by Wayne on 2015. 10. 4..
 */
public class BasicUserLevelUpgradePolicy implements UserLevelUpgradePolicy {
	@Override public boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		switch(currentLevel) {
			case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SLIVER);
			case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
			case GOLD: return false;
			default: throw new IllegalArgumentException("Unknown Level : " + currentLevel);
		}
	}
}
