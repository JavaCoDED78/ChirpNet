package com.javaded78.authenticationservice.security.filter;

import com.javaded78.authenticationservice.security.jwt.TokenUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Setter
public class JwtLogoutFilter extends OncePerRequestFilter {

	private RequestMatcher requestMatcher = new AntPathRequestMatcher(
			"/api/v1/auth/logout",
			HttpMethod.POST.name()
	);
	private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();
	private final JdbcTemplate jdbcTemplate;

	public JwtLogoutFilter(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain) throws ServletException, IOException {
		if (requestMatcher.matches(request)) {
			if (this.securityContextRepository.containsContext(request)) {
				SecurityContext context = this.securityContextRepository.loadDeferredContext(request).get();
				if (context != null && context.getAuthentication() instanceof PreAuthenticatedAuthenticationToken &&
				    context.getAuthentication().getPrincipal() instanceof TokenUser user &&
				    context.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("JWT_LOGOUT"))) {
					updateDeactivatedToken(user);
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					return;
				}
			}
			throw new AccessDeniedException("User must be authenticated with JWT");
		}
		filterChain.doFilter(request, response);
	}

	private void updateDeactivatedToken(TokenUser user) {
		this.jdbcTemplate.update("""
						INSERT INTO t_deactivated_token (id, c_keep_until) VALUES (?, ?)""",
				user.getToken().id(),
				Date.from(user.getToken().expiresAt()));
	}
}
