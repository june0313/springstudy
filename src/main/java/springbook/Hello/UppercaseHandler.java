package springbook.Hello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Wayne on 2015. 10. 13..
 */
public class UppercaseHandler implements InvocationHandler {
	private Object target;

	public UppercaseHandler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object ret = method.invoke(target, args);

		// 리턴타입과 메소드 이름이 일치하는 경우에만 부가기능을 적용한다.
		if(ret instanceof String && method.getName().startsWith("say")) {
			return ((String)ret).toUpperCase();
		}

		// 조건이 일치하지 않으면 타깃 오브젝트의 호출 결과를 그대로 리턴한다.
		return ret;
	}
}
