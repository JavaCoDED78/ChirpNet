package com.javaded78.tweetservice.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

public record ProfileResponse(

		String profileId,
		String username,
		String email,
		Integer followerCount,
		Integer followingCount,

		@JsonSerialize(using = LocalDateSerializer.class)
		@JsonDeserialize(using = LocalDateDeserializer.class)
		LocalDate joinDate,

		String bio,
		String location,
		String website,

		@JsonSerialize(using = LocalDateSerializer.class)
		@JsonDeserialize(using = LocalDateDeserializer.class)
		LocalDate birthDate,

		String avatarUrl,
		String bannerUrl
) {
}
