package com.javaded78.emailservice.consumer;

import com.javaded78.commons.event.SendEmailEvent;
import com.javaded78.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.javaded78.commons.constant.KafkaTopicConstants.SEND_EMAIL_TOPIC;

@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = SEND_EMAIL_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void setEmailListener(SendEmailEvent sendEmailEvent) {
        emailService.sendEmail(sendEmailEvent);
    }
}
