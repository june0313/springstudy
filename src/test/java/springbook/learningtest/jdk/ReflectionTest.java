package springbook.learningtest.jdk;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Wayne on 2015. 10. 11..
 */
public class ReflectionTest {
	@Test
	public void testInvokeMethod() throws Exception {
		String name = "spring";

		// length()
		assertThat(name.length(), is(6));

		Method lengthMethod = String.class.getMethod("length");
		assertThat((Integer)lengthMethod.invoke(name), is(6));

		// charAt()
		assertThat(name.charAt(0), is('s'));

		Method charMethod = String.class.getMethod("charAt", int.class);
		assertThat((Character)charMethod.invoke(name, 0), is('s'));
	}
}
