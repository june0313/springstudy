package springbook.learningtest.spring.pointcut;

/**
 * Created by Wayne on 2015. 10. 19..
 */
public class Target implements TargetInterface {
	@Override public void hello(String a) {

	}

	@Override public int minus(int a, int b) throws RuntimeException {
		return 0;
	}

	@Override public int plus(int a, int b) {
		return 0;
	}

	@Override public void hello() {

	}

	public void method() {

	}
}
