package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Wayne on 2015. 9. 18..
 */
public interface BufferedReaderCallback {
	Integer doSomethingWithReader(BufferedReader br) throws IOException;
}
