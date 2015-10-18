package springbook.user.domain.service;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;

/**
 * Created by Wayne on 2015. 10. 15..
 */
public class TxProxyFactoryBean implements FactoryBean<Object> {
	private Object target;
	private PlatformTransactionManager transactionManager;
	private String pattern;
	private Class<?> serviceInterface;

	public void setTarget(Object target) {
		this.target = target;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setServiceInterface(Class<?> serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	// FactoryBean 인터페이스 구현 메소드
	@Override public Object getObject() throws Exception {
		TransactionHandler txHandler = new TransactionHandler();
		txHandler.setTarget(this.target);
		txHandler.setTransactionManager(this.transactionManager);
		txHandler.setPattern(this.pattern);

		return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { serviceInterface }, txHandler);

	}

	@Override public Class<?> getObjectType() {
		return serviceInterface;
	}

	@Override public boolean isSingleton() {
		return false;
	}
}
