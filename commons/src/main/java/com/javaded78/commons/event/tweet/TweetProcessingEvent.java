package com.javaded78.commons.event.tweet;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TweetProcessingEvent extends ApplicationEvent {

	private final String profileId;
	private final String typeTweet;
	private final String operationTweet;

	public TweetProcessingEvent(Object tweetId, String profileId, TypeTweet typeTweet, OperationTweet operationTweet) {
		super(tweetId);
		this.profileId = profileId;
		this.typeTweet = typeTweet.getName();
		this.operationTweet = operationTweet.getOperation();
	}
}
