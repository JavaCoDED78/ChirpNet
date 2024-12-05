package com.javaded78.tweetservice.producer;

import com.javaded78.commons.event.tweet.TweetProcessingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.javaded78.commons.constant.KafkaTopicConstants.HOME_TIMELINE_TOPIC;

@Component
@RequiredArgsConstructor
public class SendMessageHomeTimelineProducer implements TweetProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	@EventListener
	public void sendMessageToKafka(TweetProcessingEvent message) {
		kafkaTemplate.send(HOME_TIMELINE_TOPIC, message);
	}
}
