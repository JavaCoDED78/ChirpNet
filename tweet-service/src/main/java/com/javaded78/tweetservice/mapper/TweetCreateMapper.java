package com.javaded78.tweetservice.mapper;

import com.javaded78.tweetservice.client.ProfileServiceClient;
import com.javaded78.tweetservice.dto.request.TweetCreateRequest;
import com.javaded78.tweetservice.model.Tweet;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TweetCreateMapper extends Mappable<Tweet, TweetCreateRequest> {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "text", expression = "java(request.text())")
	@Mapping(target = "profileId", expression = "java(profileServiceClient.getProfileIdByLoggedInUser(loggedInUser))")
	@Mapping(target = "creationDate", expression = "java(java.time.LocalDateTime.now())")
	@Mapping(target = "mediaUrls", ignore = true)
	@Mapping(target = "likes", ignore = true)
	@Mapping(target = "retweets", ignore = true)
	@Mapping(target = "replies", ignore = true)
	@Mapping(target = "views", ignore = true)
	@Mapping(target = "retweetTo", ignore = true)
	@Mapping(target = "quoteTo", expression = "java(quoteTo)")
	@Mapping(target = "replyTo", expression = "java(replyTo)")
	Tweet toEntity(
			TweetCreateRequest request,
			Tweet quoteTo,
			Tweet replyTo,
			@Context ProfileServiceClient profileServiceClient,
			@Context String loggedInUser
	);
}
