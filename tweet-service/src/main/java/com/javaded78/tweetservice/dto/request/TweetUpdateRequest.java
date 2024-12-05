package com.javaded78.tweetservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TweetUpdateRequest(

		@NotBlank(message = "{text.not_empty}")
		String text
) {
}
