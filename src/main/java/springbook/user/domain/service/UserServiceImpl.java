package springbook.user.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.domain.UserDao;

import java.util.List;

/**
 * Created by Wayne on 2015. 10. 1..
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	public static final int MIN_LOGCOUNT_FOR_SLIVER = 50;
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserLevelUpgradePolicy levelUpgradePolicy;

	@Autowired
	private MailSender mailSender;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setLevelUpgradePolicy(UserLevelUpgradePolicy levelUpgradePolicy) {
		this.levelUpgradePolicy = levelUpgradePolicy;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override public User get(String id) {
		return this.userDao.get(id);
	}

	@Override public List<User> getAll() {
		return this.userDao.getAll();
	}

	@Override public void deleteAll() {
		this.userDao.deleteAll();
	}

	@Override public void update(User user) {
		this.userDao.update(user);
	}

	@Override public void upgradeLevels() throws Exception {
		List<User> users = userDao.getAll();
		for (User user : users) {
			if (canUpgradeLevel(user)) {
				upgradeLevel(user);
			}
		}
	}

	protected void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
		sendUpgradeEmail(user);
	}

	private void sendUpgradeEmail(User user) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setFrom("useradmin@ksug.org");
		mailMessage.setSubject("Upgrade 안내");
		mailMessage.setText("사용자 님의 등급이 " + user.getLevel().name() + "로 업그레이드 되었습니다.");

		this.mailSender.send(mailMessage);
	}

	private boolean canUpgradeLevel(User user) {
		return levelUpgradePolicy.canUpgradeLevel(user);
	}

	@Override public void add(User user) {
		if(user.getLevel() == null) {
			user.setLevel(Level.BASIC);
		}

		userDao.add(user);
	}
}
