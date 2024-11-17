package com.javaded78.authenticationservice.security.service;

import com.javaded78.authenticationservice.security.jwt.Token;
import com.javaded78.authenticationservice.security.jwt.TokenUser;
import com.javaded78.authenticationservice.service.MessageSourceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.time.Instant;

@RequiredArgsConstructor
public class TokenAuthenticationUserDetailsService
        implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private final JdbcTemplate jdbcTemplate;
    private final MessageSourceService messageService;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken authenticationToken)
            throws UsernameNotFoundException {
        if (authenticationToken.getPrincipal() instanceof Token token) {
	        return new TokenUser.Builder()
	                .username(token.subject())
	                .password("nopassword")
	                .authorities(token.authorities().stream()
	                        .map(SimpleGrantedAuthority::new)
	                        .toList())
	                .token(token)
	                .enabled(true)
	                .accountNonLocked(true)
	                .credentialsNonExpired(true)
	                .accountNonExpired(Boolean.FALSE.equals(this.jdbcTemplate.queryForObject("""
	                        SELECT exists(SELECT id FROM t_deactivated_token WHERE id = ?)
	                        """, Boolean.class, token.id())) &&
	                                   token.expiresAt().isAfter(Instant.now()))
	                .build();
        }
        throw new EntityNotFoundException(messageService.generateMessage(
                "error.principal.not_found"));
    }
}
