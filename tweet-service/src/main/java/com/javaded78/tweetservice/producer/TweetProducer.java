package com.javaded78.tweetservice.producer;

import com.javaded78.commons.event.tweet.TweetProcessingEvent;

public interface TweetProducer {

	void sendMessageToKafka(TweetProcessingEvent message);
}
