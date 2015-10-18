package springbook.learningtest.template;

/**
 * Created by Wayne on 2015. 9. 21..
 */
public interface LineCallback<T> {
	T doSomethingWithLine(String line, T value);
}
