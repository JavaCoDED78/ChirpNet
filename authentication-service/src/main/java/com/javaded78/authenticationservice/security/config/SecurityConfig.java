package com.javaded78.authenticationservice.security.config;

import com.javaded78.authenticationservice.security.service.AccessTokenJwsStringDeserializer;
import com.javaded78.authenticationservice.security.service.AccessTokenJwsStringSerializer;
import com.javaded78.authenticationservice.security.service.RefreshTokenJweStringDeserializer;
import com.javaded78.authenticationservice.security.service.RefreshTokenJweStringSerializer;
import com.javaded78.authenticationservice.service.MessageSourceService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.text.ParseException;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final MessageSourceService messageSourceService;

    @Bean
    public JwtAuthenticationConfigurer jwtAuthenticationConfigurer(
            @Value("${jwt.access-token-key}") String accessTokenKey,
            @Value("${jwt.refresh-token-key}") String refreshTokenKey,
            MessageSourceService messageSourceService,
            JdbcTemplate jdbcTemplate
    ) throws ParseException, JOSEException {
        return new JwtAuthenticationConfigurer()
                .accessTokenStringSerializer(
                        AccessTokenJwsStringSerializer.builder()
                                .jwsSigner(new MACSigner(OctetSequenceKey.parse(accessTokenKey)))
                                .build()
                )
                .refreshTokenStringSerializer(
                        RefreshTokenJweStringSerializer.builder()
                                .jweEncrypter(new DirectEncrypter(OctetSequenceKey.parse(refreshTokenKey)))
                                .build()
                )
                .accessTokenStringDeserializer(
                        AccessTokenJwsStringDeserializer.builder()
                                .jwsVerifier(new MACVerifier(OctetSequenceKey.parse(accessTokenKey)))
                                .build()
                )
                .refreshTokenStringDeserializer(
                        RefreshTokenJweStringDeserializer.builder()
                                .jweDecrypter(new DirectDecrypter(OctetSequenceKey.parse(refreshTokenKey)))
                                .build()
                )
                .messageSourceService(messageSourceService)
                .jdbcTemplate(jdbcTemplate);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationConfigurer jwtAuthenticationConfigurer
    ) throws Exception {
        http.apply(jwtAuthenticationConfigurer);

        return http
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint((
                                        request, response, authException
                                ) -> {
                                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                    response.getWriter().write(messageSourceService.generateMessage("error.user.not_authenticated_by_jwt"));
                                })
                                .accessDeniedHandler((
                                        request, response, accessDeniedException
                                ) -> {
                                    response.setStatus(HttpStatus.FORBIDDEN.value());
                                    response.getWriter().write(messageSourceService.generateMessage("error.user.not_authenticated_by_jwt"));
                                })
                )
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("/api/v1/auth/register").permitAll()
                                .requestMatchers("/api/v1/auth/activate/**").permitAll()
                                .anyRequest().authenticated())
                .anonymous(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(JdbcTemplate jdbcTemplate,
                                                 MessageSourceService messageService
    ) {
        return user -> jdbcTemplate.query("SELECT * FROM t_user WHERE c_email = ?",
                        (rs, i) -> User.builder()
                                .username(rs.getString("c_email"))
                                .password(rs.getString("c_password"))
                                .authorities(
                                        jdbcTemplate.query("SELECT c_authority FROM t_user_authority WHERE id_user = ?",
                                                (rs1, i1) ->
                                                        new SimpleGrantedAuthority(rs1.getString("c_authority")),
                                                rs.getInt("id")))
                                .build(), user)
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException(messageService.generateMessage("error.entity.not_found", user)));
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

}
