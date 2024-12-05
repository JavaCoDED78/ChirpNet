package com.javaded78.tweetservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TweetCreateRequest(

		@NotBlank(message = "{text.not_empty}")
		String text
) {
}
