package com.javaded78.authenticationservice.producer;

import com.javaded78.commons.event.SendEmailEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.javaded78.commons.constant.KafkaTopicConstants.SEND_EMAIL_TOPIC;

@Component
@RequiredArgsConstructor
public class SendEmailProducer {

    private final KafkaTemplate<String, SendEmailEvent> kafkaTemplate;

    public void sendEmail(SendEmailEvent sendEmailEvent) {
        kafkaTemplate.send(SEND_EMAIL_TOPIC, sendEmailEvent);
    }

    public static SendEmailEvent toSendRegistrationEmailEvent(String toEmail, String name, String activationCode) {
        return SendEmailEvent.builder()
                .toEmail(toEmail)
                .subject("Registration message")
                .template("registration-template")
                .attributes(Map.of(
                        "name", name,
                        "message", activationCode)
                )
                .build();
    }
}
