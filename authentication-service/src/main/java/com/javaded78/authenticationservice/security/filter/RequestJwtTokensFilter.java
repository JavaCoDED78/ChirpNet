package com.javaded78.authenticationservice.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaded78.authenticationservice.security.jwt.AuthResponse;
import com.javaded78.authenticationservice.security.jwt.Token;
import com.javaded78.authenticationservice.security.service.TokenSerializer;
import com.javaded78.authenticationservice.security.factory.DefaultAccesTokenFactory;
import com.javaded78.authenticationservice.security.factory.DefaultRefreshTokenFactory;
import com.javaded78.authenticationservice.security.factory.TokenFactory;
import com.javaded78.commons.util.MessageSourceService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Setter
public class RequestJwtTokensFilter extends OncePerRequestFilter {

    private final MessageSourceService messageSourceService;
    private RequestMatcher requestMatcher = new AntPathRequestMatcher(
            "/api/v1/auth/authenticate",
            HttpMethod.POST.name()
    );
    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();
    private TokenFactory<Token, Token> accesTokenFactory = new DefaultAccesTokenFactory();
    private TokenFactory<Authentication, Token> refreshTokenFactory = new DefaultRefreshTokenFactory();
    private TokenSerializer<Token, String> accessTokenSerializer = Objects::toString;
    private TokenSerializer<Token, String> refreshTokenSerializer = Objects::toString;
    private ObjectMapper objectMapper = new ObjectMapper();

    public RequestJwtTokensFilter(MessageSourceService messageSourceService) {
        this.messageSourceService = messageSourceService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (requestMatcher.matches(request)) {
            if (this.securityContextRepository.containsContext(request)) {
                SecurityContext context = this.securityContextRepository.loadDeferredContext(request).get();
                if (context.getAuthentication() instanceof Authentication auth &&
                    !(auth instanceof PreAuthenticatedAuthenticationToken)) {
                    Token refreshToken = this.refreshTokenFactory.create(context.getAuthentication());
                    Token accessToken = this.accesTokenFactory.create(refreshToken);
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    this.objectMapper.writeValue(response.getWriter(),
                            AuthResponse.builder()
                                    .accessToken(accessTokenSerializer.serialize(accessToken))
                                    .accessTokenExpiry(accessToken.expiresAt().toString())
                                    .refreshToken(refreshTokenSerializer.serialize(refreshToken))
                                    .refreshTokenExpiry(refreshToken.expiresAt().toString())
                                    .build());
                    return;
                }
            }
            throw new AccessDeniedException(messageSourceService.generateMessage("error.user.not_authenticated"));
        }
        filterChain.doFilter(request, response);
    }
}
