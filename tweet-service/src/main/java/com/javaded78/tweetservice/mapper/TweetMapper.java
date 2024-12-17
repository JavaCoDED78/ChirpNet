package com.javaded78.tweetservice.mapper;

import com.javaded78.tweetservice.client.ProfileServiceClient;
import com.javaded78.tweetservice.dto.response.TweetResponse;
import com.javaded78.tweetservice.model.Tweet;
import com.javaded78.tweetservice.service.TweetService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TweetMapper {

	@Mapping(target = "profile", expression = "java(profileServiceClient.getProfileById(tweet.getProfileId()))")
	@Mapping(target = "quoteTo", expression = "java(this.toResponse(tweet.getQuoteTo(), loggedInUser, tweetService, profileServiceClient))")
	@Mapping(target = "replyTo", expression = "java(this.toResponse(tweet.getReplyTo(), loggedInUser, tweetService, profileServiceClient))")
	@Mapping(target = "retweetTo", expression = "java(this.toResponse(tweet.getRetweetTo(), loggedInUser, tweetService, profileServiceClient))")
	@Mapping(target = "likes", expression = "java(tweetService.countLikesForTweet(tweet.getId()))")
	@Mapping(target = "replies", expression = "java(tweetService.countRepliesForTweet(tweet.getId()))")
	@Mapping(target = "views", expression = "java(tweetService.countViewsForTweet(tweet.getId()))")
	@Mapping(target = "retweets", expression = "java(tweetService.countRetweetsForTweet(tweet.getId()))")
	@Mapping(target = "isRetweeted", expression = "java(tweetService.isTweetRetweetedByLoggedInUser(tweet.getId(), loggedInUser, profileServiceClient))")
	@Mapping(target = "isLiked", expression = "java(tweetService.isTweetLikedByLoggedInUser(tweet.getId(), loggedInUser, profileServiceClient))")
	@Mapping(target = "isBelongs", expression = "java(profileServiceClient.getProfileById(tweet.getProfileId()).getEmail().equals(loggedInUser))")
	TweetResponse toTweetResponse(
			Tweet tweet,
			@Context String loggedInUser,
			@Context TweetService tweetService,
			@Context ProfileServiceClient profileServiceClient
	);
}
