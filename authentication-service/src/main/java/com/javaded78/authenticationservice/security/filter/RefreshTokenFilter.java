package com.javaded78.authenticationservice.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaded78.authenticationservice.security.factory.DefaultAccesTokenFactory;
import com.javaded78.authenticationservice.security.factory.TokenFactory;
import com.javaded78.authenticationservice.security.jwt.AuthResponse;
import com.javaded78.authenticationservice.security.jwt.Token;
import com.javaded78.authenticationservice.security.jwt.TokenUser;
import com.javaded78.authenticationservice.security.service.TokenSerializer;
import com.javaded78.commons.util.MessageSourceService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Setter
public class RefreshTokenFilter extends OncePerRequestFilter {

	private final MessageSourceService messageSourceService;
	private RequestMatcher requestMatcher = new AntPathRequestMatcher(
			"/api/v1/auth/refresh",
			HttpMethod.POST.name()
	);
	private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();
	private TokenFactory<Token, Token> accessTokenFactory = new DefaultAccesTokenFactory();
	private TokenSerializer<Token, String> accessTokenStringSerializer = Object::toString;
	private ObjectMapper objectMapper = new ObjectMapper();

	public RefreshTokenFilter(MessageSourceService messageSourceService) {
		this.messageSourceService = messageSourceService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain) throws ServletException, IOException {

		if (requestMatcher.matches(request)) {
			if (this.securityContextRepository.containsContext(request)) {
				SecurityContext context = this.securityContextRepository.loadDeferredContext(request).get();
				if (context != null && context.getAuthentication() instanceof PreAuthenticatedAuthenticationToken &&
				    context.getAuthentication().getPrincipal() instanceof TokenUser user &&
				    context.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("JWT_REFRESH"))) {
					Token accessToken = this.accessTokenFactory.create(user.getToken());
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					this.objectMapper.writeValue(response.getWriter(),
							AuthResponse.builder()
									.accessToken(accessTokenStringSerializer.serialize(accessToken))
									.accessTokenExpiry(accessToken.expiresAt().toString())
									.build());
					return;
				}
			}
			throw new AccessDeniedException(messageSourceService.generateMessage("error.user.not_authenticated_by_jwt"));
		}
		filterChain.doFilter(request, response);
	}
}
