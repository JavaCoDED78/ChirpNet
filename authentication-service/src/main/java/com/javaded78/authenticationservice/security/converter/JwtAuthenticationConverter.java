package com.javaded78.authenticationservice.security.converter;

import com.javaded78.authenticationservice.security.jwt.Token;
import com.javaded78.authenticationservice.security.service.TokenDeserializer;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@AllArgsConstructor
public class JwtAuthenticationConverter implements AuthenticationConverter {

    private final TokenDeserializer<String, Token> accessTokenStringDeserializer;
    private final TokenDeserializer<String, Token> refreshTokenStringDeserializer;

    @Override
    public Authentication convert(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization instanceof String auth && auth.startsWith("Bearer ")) {
            String token = auth.substring("Bearer ".length());
            Token tokenResult = accessTokenStringDeserializer.deserialize(token);
            if (tokenResult == null) {
                tokenResult = refreshTokenStringDeserializer.deserialize(token);
            }
            if (tokenResult != null) {
                return new PreAuthenticatedAuthenticationToken(tokenResult, token);
            }
        }
        return null;
    }
}
