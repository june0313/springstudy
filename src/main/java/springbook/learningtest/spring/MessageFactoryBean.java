package springbook.learningtest.spring;

import org.springframework.beans.factory.FactoryBean;

/**
 * Created by Wayne on 2015. 10. 15..
 */
public class MessageFactoryBean implements FactoryBean<Message> {
	private String text;

	public void setText(String text) {
		this.text = text;
	}

	@Override public Message getObject() throws Exception {
		return Message.newMessage(this.text);
	}

	@Override public Class<? extends Message> getObjectType() {
		return Message.class;
	}

	@Override public boolean isSingleton() {
		return false;
	}
}
