package com.javaded78.emailservice.consumer;

import com.javaded78.commons.event.SendEmailEvent;
import com.javaded78.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailConsumer {

    public static final String SEND_EMAIL_TOPIC = "authentication-service.user.send-email";
    private final EmailService emailService;

    @KafkaListener(topics = SEND_EMAIL_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void setEmailListener(SendEmailEvent sendEmailEvent) {
        emailService.sendEmail(sendEmailEvent);
    }
}
