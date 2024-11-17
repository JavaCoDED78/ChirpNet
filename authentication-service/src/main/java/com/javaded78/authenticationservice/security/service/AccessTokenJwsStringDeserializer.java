package com.javaded78.authenticationservice.security.service;

import com.javaded78.authenticationservice.security.jwt.Token;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.util.UUID;

@Slf4j
@Builder
public class AccessTokenJwsStringDeserializer implements TokenDeserializer<String, Token> {

    private final JWSVerifier jwsVerifier;

    @Override
    public Token deserialize(String from) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(from);
            if (signedJWT.verify(this.jwsVerifier)) {
                JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
                return Token.builder()
                        .id(UUID.fromString(jwtClaimsSet.getJWTID()))
                        .subject(jwtClaimsSet.getSubject())
                        .createdAt(jwtClaimsSet.getIssueTime().toInstant())
                        .expiresAt(jwtClaimsSet.getExpirationTime().toInstant())
                        .authorities(jwtClaimsSet.getStringListClaim("authorities"))
                        .build();
            }
        } catch (JOSEException | ParseException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
