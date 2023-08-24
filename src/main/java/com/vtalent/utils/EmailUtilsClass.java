package com.vtalent.utils;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Configuration
public class EmailUtilsClass {

	@Autowired
	private JavaMailSender javaMailSender;

	public boolean sendEmail(String to, String subject, String body, File file) {

		boolean isSentMail = false;

		try {
			MimeMessage createMimeMessage = javaMailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(createMimeMessage);

			helper.setTo(to);

			helper.setSubject(subject);

			helper.setText(body, true);

			helper.addAttachment("His-Elig-Notice", file);

			javaMailSender.send(createMimeMessage);

			isSentMail = true;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return isSentMail;

	}
}
