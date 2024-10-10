package com.javaded78.authenticationservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @Email(message = "{email.invalid}")
        @NotBlank(message = "{email.notnull}")
        String email,

        @Size(min = 6, max = 32, message = "{password.size}")
        @NotBlank(message = "{password.notnull}")
        String password,

        @Size(min = 5, max = 50, message = "{username.size}")
        @NotBlank(message = "{username.notnull}")
        String username
) {

}
