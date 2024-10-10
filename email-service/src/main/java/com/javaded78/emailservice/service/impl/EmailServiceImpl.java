package com.javaded78.emailservice.service.impl;

import com.javaded78.emailservice.event.SendEmailEvent;
import com.javaded78.emailservice.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

    @SneakyThrows
    @Override
    public void sendEmail(SendEmailEvent sendEmailEvent) {
        Context typleafContext = new Context();
        typleafContext.setVariables(sendEmailEvent.attributes());
        String emailHtmlBody = thymeleafTemplateEngine.process(sendEmailEvent.template(), typleafContext);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(username);
        helper.setTo(sendEmailEvent.toEmail());
        helper.setSubject(sendEmailEvent.subject());
        helper.setText(emailHtmlBody, true);
        mailSender.send(message);
    }
}
