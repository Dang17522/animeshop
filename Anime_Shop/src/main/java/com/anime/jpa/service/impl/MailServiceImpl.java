package com.anime.jpa.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.anime.jpa.service.MailService;
import com.anime.model.MailInfo;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void send(MailInfo mailInfo) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setFrom(mailInfo.getFrom());
		helper.setTo(mailInfo.getTo());
		helper.setSubject(mailInfo.getSubject());
		helper.setText(mailInfo.getBody(), true);
		helper.setReplyTo(mailInfo.getFrom());

		mailSender.send(message);

	}

	@Override
	public void send(String to, String subject, String body) throws MessagingException {
		this.send(new MailInfo(to, subject, body));

	}
}