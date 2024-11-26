package com.javaded78.profileservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
public record ProfileResponse(
		String profileId,
		String username,
		String email,
		Integer followerCount,
		Integer followingCount,

		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		@JsonFormat(pattern = "yyyy-MM-dd")
		@JsonSerialize(using = LocalDateSerializer.class)
		@JsonDeserialize(using = LocalDateDeserializer.class)
		LocalDate joinDate,

		String bio,
		String location,
		String website,

		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		@JsonFormat(pattern = "yyyy-MM-dd")
		@JsonSerialize(using = LocalDateSerializer.class)
		@JsonDeserialize(using = LocalDateDeserializer.class)
		LocalDate birthDate,

		String avatarUrl,
		String bannerUrl
) implements Serializable {
}
