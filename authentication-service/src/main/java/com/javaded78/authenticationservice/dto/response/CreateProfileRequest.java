package com.javaded78.authenticationservice.dto.response;

import java.time.LocalDate;

public record CreateProfileRequest(
        String username,
        String email,
        LocalDate joinDate
) {

}
