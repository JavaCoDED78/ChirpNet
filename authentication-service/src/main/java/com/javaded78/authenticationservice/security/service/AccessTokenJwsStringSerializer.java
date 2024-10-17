package com.javaded78.authenticationservice.security.service;

import com.javaded78.authenticationservice.security.jwt.Token;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@Builder
public class AccessTokenJwsStringSerializer implements TokenSerializer<Token, String> {

    private final JWSSigner jwsSigner;
    private final JWSAlgorithm jwsAlgorithm = JWSAlgorithm.HS256;

    @Override
    public String serialize(Token token) {
        JWSHeader jwsHeader = new JWSHeader.Builder(this.jwsAlgorithm)
                .keyID(token.id().toString())
                .build();
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(token.id().toString())
                .subject(token.subject())
                .issueTime(Date.from(token.createdAt()))
                .expirationTime(Date.from(token.expiresAt()))
                .claim("authorities", token.authorities())
                .build();
        SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);
        try {
            signedJWT.sign(this.jwsSigner);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
