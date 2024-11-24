package com.javaded78.profileservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record UpdateProfileRequest(
		@Size(min = 5, max = 50, message = "{username.size}")
		@NotBlank(message = "{username.not-null}")
		String username,

		@Size(max = 160, message = "{bio.size}")
		String bio,

		@Size(max = 30, message = "{location.size}")
		String location,

		@Size(max = 100, message = "{website.size}")
		String website,

		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		@JsonFormat(pattern = "yyyy-MM-dd")
		@Past LocalDate birthDate
) {
}
