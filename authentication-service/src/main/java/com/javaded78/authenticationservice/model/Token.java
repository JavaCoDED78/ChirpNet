package com.javaded78.authenticationservice.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record Token(UUID id, String subject, List<String> authorities, Instant createdAt, Instant expiresAt) {
}
