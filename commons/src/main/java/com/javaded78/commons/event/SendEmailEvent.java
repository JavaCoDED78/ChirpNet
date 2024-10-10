package com.javaded78.commons.event;

import lombok.Builder;

import java.util.Map;

@Builder
public record SendEmailEvent(
        String toEmail,
        String subject,
        String template,
        Map<String, Object> attributes) {
}
