package com.javaded78.authenticationservice.security.service;

import com.javaded78.authenticationservice.security.jwt.Token;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.util.UUID;

@Slf4j
@Builder
public class RefreshTokenJweStringDeserializer implements TokenDeserializer<String, Token> {

    private final JWEDecrypter jweDecrypter;

    @Override
    public Token deserialize(String from) {
        try {
            EncryptedJWT encryptedJWT = EncryptedJWT.parse(from);
            encryptedJWT.decrypt(this.jweDecrypter);
            JWTClaimsSet jwtClaimsSet = encryptedJWT.getJWTClaimsSet();
            return Token.builder()
                    .id(UUID.fromString(jwtClaimsSet.getJWTID()))
                    .subject(jwtClaimsSet.getSubject())
                    .createdAt(jwtClaimsSet.getIssueTime().toInstant())
                    .expiresAt(jwtClaimsSet.getExpirationTime().toInstant())
                    .authorities(jwtClaimsSet.getStringListClaim("authorities"))
                    .build();
        } catch (JOSEException | ParseException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
