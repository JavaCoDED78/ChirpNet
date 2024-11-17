package com.javaded78.emailservice.service.impl;

import com.javaded78.commons.event.SendRegistrationCodeEmailEvent;
import com.javaded78.emailservice.exception.RetryableException;
import com.javaded78.emailservice.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender mailSender;
	private final SpringTemplateEngine thymeleafTemplateEngine;

	@Value("${spring.mail.username}")
	private String username;

	@Override
	public void sendEmail(SendRegistrationCodeEmailEvent sendRegistrationCodeEmailEvent) {
		Context typleafContext = new Context();
		typleafContext.setVariables(sendRegistrationCodeEmailEvent.attributes());
		String emailHtmlBody = thymeleafTemplateEngine.process(sendRegistrationCodeEmailEvent.template(), typleafContext);
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(username);
			helper.setTo(sendRegistrationCodeEmailEvent.toEmail());
			helper.setSubject(sendRegistrationCodeEmailEvent.subject());
			helper.setText(emailHtmlBody, true);
		} catch (MessagingException e) {
			throw new RetryableException(e);
		}
		mailSender.send(message);
	}
}
