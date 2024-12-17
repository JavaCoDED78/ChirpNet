package com.javaded78.tweetservice.service.factory;

import com.javaded78.commons.event.tweet.OperationTweet;
import com.javaded78.commons.event.tweet.TweetProcessingEvent;
import com.javaded78.commons.event.tweet.TypeTweet;
import com.javaded78.tweetservice.model.Tweet;
import org.springframework.stereotype.Component;

@Component
public class TweetProcessingEventFactory implements EventFactory<TweetProcessingEvent> {

	@Override
	public TweetProcessingEvent createEvent(Tweet tweet, TypeTweet typeTweet, OperationTweet operationTweet) {
		return new TweetProcessingEvent(
				tweet.getTweetId(),
				tweet.getProfileId(),
				typeTweet,
				operationTweet
		);
	}
}
