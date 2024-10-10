package com.javaded78.authenticationservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(

        @Email(message = "{email.invalid}")
        @NotBlank(message = "{email.notnull}")
        String email,

        @Size(min = 6, max = 32, message = "{password.size}")
        @NotBlank(message = "{password.notnull}")
        String password
) {
}
