package com.javaded78.authenticationservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(

        @Email(message = "Email must be valid")
        @NotBlank(message = "Email must not be blank")
        String email,

        @Size(min = 6, max = 32, message = "Password must be between {min} and {max} characters")
        @NotBlank(message = "Password must not be blank")
        String password
) {
}
