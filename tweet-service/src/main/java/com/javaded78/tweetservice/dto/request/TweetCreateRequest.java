package com.javaded78.tweetservice.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Set;

public record TweetCreateRequest(

		@NotBlank(message = "{text.not_empty}")
		String text,

		Set<String> mediaUrls,
		List<String> hashtags
) {
}
