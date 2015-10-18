package springbook.user.domain.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Created by Wayne on 2015. 10. 8..
 */
public class DummyMailSender implements MailSender {
	@Override
	public void send(SimpleMailMessage simpleMessage) throws MailException {

	}

	@Override
	public void send(SimpleMailMessage[] simpleMessages) throws MailException {

	}
}
