package com.javaded78.tweetservice.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Set;

public record TweetResponse(

		Long id,
		TweetResponse replyTo,
		TweetResponse retweetTo,
		ProfileResponse profile,
		String text,
		Set<String> mediaUrls,
		TweetResponse quoteTo,
		Integer retweets,
		Integer replies,
		Integer likes,
		Integer views,

		@JsonSerialize(using = LocalDateTimeSerializer.class)
		@JsonDeserialize(using = LocalDateTimeDeserializer.class)
		LocalDateTime creationDate,

		Boolean isRetweeted,
		Boolean isLiked,
		Boolean isBelongs
) {
}
