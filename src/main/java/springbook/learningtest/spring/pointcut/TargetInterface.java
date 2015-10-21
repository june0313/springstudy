package springbook.learningtest.spring.pointcut;

/**
 * Created by Wayne on 2015. 10. 19..
 */
public interface TargetInterface {
	void hello();
	void hello(String a);
	int minus(int a, int b) throws RuntimeException;
	int plus(int a, int b);
}
