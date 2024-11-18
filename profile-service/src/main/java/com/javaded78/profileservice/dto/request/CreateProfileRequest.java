package com.javaded78.profileservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateProfileRequest(

		@Size(min = 5, max = 50, message = "{username.size}")
		@NotBlank(message = "{username.not-null}")
        String username,

		@Email(message = "{email.invalid}")
        String email,

		@NotNull(message = "{joinDate.not-null}")
        LocalDate joinDate
) {

}
