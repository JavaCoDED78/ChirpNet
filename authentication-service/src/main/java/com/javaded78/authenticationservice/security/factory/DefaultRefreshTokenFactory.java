package com.javaded78.authenticationservice.security.factory;

import com.javaded78.authenticationservice.security.jwt.Token;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Setter
public class DefaultRefreshTokenFactory implements TokenFactory<Authentication, Token> {

    private Duration tokenTtl = Duration.ofDays(1);

    @Override
    public Token create(Authentication authentication) {
        List<String> authorities = new LinkedList<>();
        authorities.add("JWT_REFRESH");
        authorities.add("JWT_LOGOUT");
        Instant now = Instant.now();
        authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> "GRANT_" + authority)
                .forEach(authorities::add);
        return Token.builder()
                .id(UUID.randomUUID())
                .subject(authentication.getName())
                .createdAt(now)
                .authorities(authorities)
                .expiresAt(now.plus(tokenTtl))
                .build();
    }
}
