package com.javaded78.authenticationservice.producer;

import com.javaded78.commons.event.auth.SendRegistrationCodeEmailEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.javaded78.commons.constant.KafkaTopicConstants.SEND_EMAIL_TOPIC;

@Component
@RequiredArgsConstructor
public class SendRegistrationEmailProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendRegistrationEmail(SendRegistrationCodeEmailEvent sendRegistrationCodeEmailEvent) {
        kafkaTemplate.send(SEND_EMAIL_TOPIC, sendRegistrationCodeEmailEvent);
    }

    public static SendRegistrationCodeEmailEvent toSendRegistrationEmailEvent(String toEmail, String activationCode) {
        return SendRegistrationCodeEmailEvent.builder()
                .toEmail(toEmail)
                .subject("Registration message")
                .template("registration-template")
                .attributes(Map.of(
                        "name", toEmail,
                        "code", activationCode)
                )
                .build();
    }
}
