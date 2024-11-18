package com.javaded78.profileservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

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
		LocalDate joinDate,

		String bio,
		String location,
		String website,

		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		@JsonFormat(pattern = "yyyy-MM-dd")
		LocalDate birthDate,

		String avatarUrl,
		String bannerUrl
) {
}
