package springbook.user.domain.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import springbook.user.domain.User;

/**
 * Created by Wayne on 2015. 10. 8..
 */
public class UserServiceTx implements UserService {
	// 타깃 오브젝트
	private UserService userService;
	private PlatformTransactionManager transactionManager;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	// 메소드 구현
	@Override public void upgradeLevels() throws Exception {
		// 부가 기능 수행
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {

			// 위임
			this.userService.upgradeLevels();

		// 부가 기능 수행
			this.transactionManager.commit(status);
		} catch(RuntimeException e) {
			this.transactionManager.rollback(status);
			throw e;
		}
	}

	// 메소드 구현과 위임
	@Override public void add(User user) {
		userService.add(user);
	}
}
