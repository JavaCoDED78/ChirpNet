package com.javaded78.authenticationservice.security.jwt;

import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
public record Token(
        UUID id,
        String subject,
        List<String> authorities,
        Instant createdAt,
        Instant expiresAt
) {
}
