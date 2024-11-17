package com.javaded78.emailservice.consumer;

import com.javaded78.commons.event.SendRegistrationCodeEmailEvent;
import com.javaded78.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.javaded78.commons.constant.KafkaTopicConstants.SEND_EMAIL_TOPIC;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = SEND_EMAIL_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
public class EmailHandler {

	private final EmailService emailService;

	@KafkaHandler
	public void setEmailListener(SendRegistrationCodeEmailEvent sendRegistrationCodeEmailEvent) {
		emailService.sendEmail(sendRegistrationCodeEmailEvent);
	}
}
