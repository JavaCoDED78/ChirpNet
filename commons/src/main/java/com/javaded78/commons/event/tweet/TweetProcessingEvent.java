package com.javaded78.commons.event.tweet;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class TweetProcessingEvent extends ApplicationEvent {

	private final UUID profileId;
	private final String typeTweet;
	private final String operationTweet;

	public TweetProcessingEvent(Object tweetId, UUID profileId, TypeTweet typeTweet, OperationTweet operationTweet) {
		super(tweetId);
		this.profileId = profileId;
		this.typeTweet = typeTweet.getName();
		this.operationTweet = operationTweet.getOperation();
	}
}
