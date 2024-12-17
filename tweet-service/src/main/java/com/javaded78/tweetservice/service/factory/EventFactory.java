package com.javaded78.tweetservice.service.factory;

import com.javaded78.commons.event.tweet.OperationTweet;
import com.javaded78.commons.event.tweet.TypeTweet;
import com.javaded78.tweetservice.model.Tweet;

public interface EventFactory<E> {

	public E createEvent(Tweet tweet, TypeTweet typeTweet, OperationTweet operationTweet);
}
