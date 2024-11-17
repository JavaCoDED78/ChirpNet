package com.javaded78.authenticationservice.security.factory;

import com.javaded78.authenticationservice.security.jwt.Token;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;

@Setter
public class DefaultAccesTokenFactory implements TokenFactory<Token, Token> {

    private Duration tokenTtl = Duration.ofMinutes(5);

    @Override
    public Token create(Token token) {
        Instant now = Instant.now();
        return Token.builder()
                .id(token.id())
                .subject(token.subject())
                .authorities(token.authorities().stream()
                        .filter(authority -> authority.startsWith("GRANT_"))
                        .map(authority -> authority.substring(6))
                        .toList())
                .createdAt(now)
                .expiresAt(now.plus(tokenTtl))
                .build();
    }
}
