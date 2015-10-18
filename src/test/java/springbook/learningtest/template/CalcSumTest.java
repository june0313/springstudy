package springbook.learningtest.template;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Wayne on 2015. 9. 15..
 */
public class CalcSumTest {
	private Calculator calculator;
	private String numFilePath;

	@Before
	public void setUp() throws Exception {
		this.calculator = new Calculator();
		this.numFilePath = "/Users/wayne/Study/MySpring/src/test/resources/numbers.txt";
	}

	@Test
	public void testSumOfNumbers() throws Exception {
		assertThat(calculator.calcSum(this.numFilePath), is(10));
	}

	@Test
	public void testMultiplyOfNumbers() throws Exception {
		assertThat(calculator.calcMultiply(this.numFilePath), is(24));
	}

	@Test
	public void testConcatenateStrings() throws Exception {
		assertThat(calculator.concatenate(this.numFilePath), is("1234"));
	}
}
