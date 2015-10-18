package springbook.learningtest.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Wayne on 2015. 10. 15..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class FactoryBeanTest {
	@Autowired
	private ApplicationContext context;

	@Test
	public void testGetMessageFromFactoryBean() throws Exception {
		Object message = context.getBean("message");
		assertThat(message, instanceOf(Message.class));
		assertThat(((Message)message).getText(), is("Factory Bean"));
	}

	@Test
	public void testGetFactoryBean() throws Exception {
		Object factory = context.getBean("&message");
		assertThat(factory, instanceOf(MessageFactoryBean.class));
	}
}
