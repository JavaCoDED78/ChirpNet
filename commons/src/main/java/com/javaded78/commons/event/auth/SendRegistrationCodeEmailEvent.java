package com.javaded78.commons.event.auth;

import lombok.Builder;

import java.util.Map;

@Builder
public record SendRegistrationCodeEmailEvent(
        String toEmail,
        String subject,
        String template,
        Map<String, Object> attributes) {
}
