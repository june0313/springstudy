package springbook.Hello;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Wayne on 2015. 10. 11..
 */
public class HelloTargetTest {
	@Test
	public void testSimpleProxy() throws Exception {
		Hello hello = new HelloTarget();
		assertThat(hello.sayHello("Toby"), is("Hello Toby"));
		assertThat(hello.sayHi("Toby"), is("Hi Toby"));
		assertThat(hello.sayThankyou("Toby"), is("Thank you Toby"));
	}
}