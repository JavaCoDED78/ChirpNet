package com.javaded78.authenticationservice.security.config;

import com.javaded78.authenticationservice.security.converter.JwtAuthenticationConverter;
import com.javaded78.authenticationservice.security.filter.JwtLogoutFilter;
import com.javaded78.authenticationservice.security.filter.RefreshTokenFilter;
import com.javaded78.authenticationservice.security.filter.RequestJwtTokensFilter;
import com.javaded78.authenticationservice.security.jwt.Token;
import com.javaded78.authenticationservice.security.service.TokenAuthenticationUserDetailsService;
import com.javaded78.authenticationservice.security.service.TokenDeserializer;
import com.javaded78.authenticationservice.security.service.TokenSerializer;
import com.javaded78.commons.util.MessageSourceService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class JwtAuthenticationConfigurer
        extends AbstractHttpConfigurer<JwtAuthenticationConfigurer, HttpSecurity> {

    private TokenSerializer<Token, String> accessTokenStringSerializer = Object::toString;
    private TokenSerializer<Token, String> refreshTokenStringSerializer = Object::toString;
    private TokenDeserializer<String, Token> accessTokenStringDeserializer;
    private TokenDeserializer<String, Token> refreshTokenStringDeserializer;
    private MessageSourceService messageSourceService;
    private JdbcTemplate jdbcTemplate;

    @Override
    public void init(HttpSecurity builder) throws Exception {
        @SuppressWarnings("unchecked")
        CsrfConfigurer<HttpSecurity> csrfConfigurer = builder.getConfigurer(CsrfConfigurer.class);
        if (csrfConfigurer != null) {
            csrfConfigurer.ignoringRequestMatchers(
                    new AntPathRequestMatcher("/api/v1/auth/register", "POST"),
                    new AntPathRequestMatcher("/api/v1/auth/authenticate", "POST"),
                    new AntPathRequestMatcher("/api/v1/auth/activate", "GET")
            );
        }
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        RequestJwtTokensFilter requestJwtTokensFilter = new RequestJwtTokensFilter(messageSourceService);
        requestJwtTokensFilter.setAccessTokenSerializer(this.accessTokenStringSerializer);
        requestJwtTokensFilter.setRefreshTokenSerializer(this.refreshTokenStringSerializer);

        AuthenticationFilter jwtAuthenticationFilter = new AuthenticationFilter(
                builder.getSharedObject(AuthenticationManager.class),
                new JwtAuthenticationConverter(
                        this.accessTokenStringDeserializer,
                        this.refreshTokenStringDeserializer,
                        this.messageSourceService
                )
        );
        jwtAuthenticationFilter
                .setSuccessHandler((request, response, authentication) ->
                    CsrfFilter.skipRequest(request)
                );
        jwtAuthenticationFilter
                .setFailureHandler((request, response, exception) ->
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN)
                );

        PreAuthenticatedAuthenticationProvider authenticationProvider = new PreAuthenticatedAuthenticationProvider();
        authenticationProvider.setPreAuthenticatedUserDetailsService(
                new TokenAuthenticationUserDetailsService(jdbcTemplate, messageSourceService));

        RefreshTokenFilter refreshTokenFilter = new RefreshTokenFilter(this.messageSourceService);
        refreshTokenFilter.setAccessTokenStringSerializer(this.accessTokenStringSerializer);

        JwtLogoutFilter jwtLogoutFilter = new JwtLogoutFilter(this.jdbcTemplate, this.messageSourceService);

        builder
                .addFilterAfter(requestJwtTokensFilter, ExceptionTranslationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, CsrfFilter.class)
                .addFilterAfter(refreshTokenFilter, ExceptionTranslationFilter.class)
                .addFilterAfter(jwtLogoutFilter, ExceptionTranslationFilter.class)
                .authenticationProvider(authenticationProvider);
    }

    public JwtAuthenticationConfigurer refreshTokenStringSerializer(
            TokenSerializer<Token, String> refreshTokenStringSerializer) {
        this.refreshTokenStringSerializer = refreshTokenStringSerializer;
        return this;
    }

    public JwtAuthenticationConfigurer accessTokenStringSerializer(
            TokenSerializer<Token, String> accessTokenStringSerializer) {
        this.accessTokenStringSerializer = accessTokenStringSerializer;
        return this;
    }

    public JwtAuthenticationConfigurer accessTokenStringDeserializer(
            TokenDeserializer<String, Token> accessTokenStringDeserializer) {
        this.accessTokenStringDeserializer = accessTokenStringDeserializer;
        return this;
    }

    public JwtAuthenticationConfigurer refreshTokenStringDeserializer(
            TokenDeserializer<String, Token> refreshTokenStringDeserializer) {
        this.refreshTokenStringDeserializer = refreshTokenStringDeserializer;
        return this;
    }

    public JwtAuthenticationConfigurer jdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        return this;
    }

    public JwtAuthenticationConfigurer messageSourceService(MessageSourceService messageSourceService) {
        this.messageSourceService = messageSourceService;
        return this;
    }
}
