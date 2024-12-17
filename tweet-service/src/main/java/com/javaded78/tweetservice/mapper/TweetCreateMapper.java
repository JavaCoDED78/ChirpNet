package com.javaded78.tweetservice.mapper;

import com.javaded78.tweetservice.client.ProfileServiceClient;
import com.javaded78.tweetservice.dto.request.TweetCreateRequest;
import com.javaded78.tweetservice.model.Tweet;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TweetCreateMapper extends Mappable<Tweet, TweetCreateRequest> {

	@Mapping(target = "tweetId", expression ="java(java.util.UUID.randomUUID())")
	@Mapping(target = "content", expression = "java(request.text())")
	@Mapping(target = "profileId", expression = "java(profileServiceClient.getProfileIdByLoggedInUser(loggedInUser))")
	@Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
	@Mapping(target = "retweetOfId", expression = "java(retweetOf.getTweetId())")
	@Mapping(target = "quoteOfId", expression = "java(quoteOf.getTweetId())")
	@Mapping(target = "replyToId", expression = "java(replyTo.getTweetId())")
	Tweet toEntity(
			TweetCreateRequest request,
			Tweet quoteOf,
			Tweet replyTo,
			Tweet retweetOf,
			@Context ProfileServiceClient profileServiceClient,
			@Context String loggedInUser
	);
}
