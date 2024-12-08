package com.javaded78.tweetservice.mapper;

import com.javaded78.tweetservice.dto.response.TweetResponse;
import com.javaded78.tweetservice.model.Tweet;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TweetMapper {
//
//	@Mapping(target = "profile", expression = "java(profileServiceClient.getProfileById(tweet.getProfileId()))")
//	@Mapping(target = "quoteTo", expression = "java(this.toResponse(tweet.getQuoteTo(), loggedInUser, tweetUtil, profileServiceClient))")
//	@Mapping(target = "replyTo", expression = "java(this.toResponse(tweet.getReplyTo(), loggedInUser, tweetUtil, profileServiceClient))")
//	@Mapping(target = "retweetTo", expression = "java(this.toResponse(tweet.getRetweetTo(), loggedInUser, tweetUtil, profileServiceClient))")
//	@Mapping(target = "likes", expression = "java(tweetUtil.countLikesForTweet(tweet.getId()))")
//	@Mapping(target = "replies", expression = "java(tweetUtil.countRepliesForTweet(tweet.getId()))")
//	@Mapping(target = "views", expression = "java(tweetUtil.countViewsForTweet(tweet.getId()))")
//	@Mapping(target = "retweets", expression = "java(tweetUtil.countRetweetsForTweet(tweet.getId()))")
//	@Mapping(target = "isRetweeted", expression = "java(tweetUtil.isTweetRetweetedByLoggedInUser(tweet.getId(), loggedInUser, profileServiceClient))")
//	@Mapping(target = "isLiked", expression = "java(tweetUtil.isTweetLikedByLoggedInUser(tweet.getId(), loggedInUser, profileServiceClient))")
//	@Mapping(target = "isBelongs", expression = "java(profileServiceClient.getProfileById(tweet.getProfileId()).getEmail().equals(loggedInUser))")
//	TweetResponse toResponse(
//			Tweet tweet,
//			@Context String loggedInUser,
//			@Context TweetUtil tweetUtil,
//			@Context ProfileServiceClient profileServiceClient
//	);
}
