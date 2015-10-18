package springbook.learningtest.spring;

/**
 * Created by Wayne on 2015. 10. 15..
 */
public class Message {
	private String text;

	private Message(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public static Message newMessage(String text) {
		return new Message(text);
	}
}
