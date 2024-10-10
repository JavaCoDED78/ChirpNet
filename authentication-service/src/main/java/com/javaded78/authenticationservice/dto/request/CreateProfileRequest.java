package com.javaded78.authenticationservice.dto.request;

import java.time.LocalDate;

public record CreateProfileRequest(
        String username,
        String email,
        LocalDate joinDate
) {

}
