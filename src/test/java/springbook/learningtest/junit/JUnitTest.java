package springbook.learningtest.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;


/**
 * Created by Wayne on 2015. 9. 14..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/junit.xml")
public class JUnitTest {
	@Autowired
	ApplicationContext context;

	static Set<JUnitTest> testObjects = new HashSet<JUnitTest>();
	static ApplicationContext contextObject = null;

	@Test
	public void test1() throws Exception {
		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);

		assertThat(contextObject == null || contextObject == this.context, is(true));
		contextObject = this.context;
	}

	@Test
	public void test2() throws Exception {
		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);

		assertTrue(contextObject == null || contextObject == this.context);
		contextObject = this.context;
	}

	@Test
	public void test3() throws Exception {
		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);

		//assertThat(contextObject, either(is(nullValue())).or(is(this.context)));
		assertTrue(contextObject == null || contextObject == this.context);
		contextObject = this.context;
	}
}
