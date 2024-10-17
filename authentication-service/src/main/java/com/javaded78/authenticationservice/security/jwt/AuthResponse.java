package com.javaded78.authenticationservice.security.jwt;

import lombok.Builder;

@Builder
public record AuthResponse(
        String accessToken,
        String accessTokenExpiry,
        String refreshToken,
        String refreshTokenExpiry) {
}
