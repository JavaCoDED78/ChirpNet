package com.javaded78.authenticationservice.security.service;

import com.javaded78.authenticationservice.security.jwt.Token;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEEncrypter;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@Builder
public class RefreshTokenJweStringSerializer implements TokenSerializer<Token, String> {

    private final JWEEncrypter jweEncrypter;
    private final JWEAlgorithm jweAlgorithm = JWEAlgorithm.DIR;;
    private final EncryptionMethod encryptionMethod = EncryptionMethod.A128GCM;;

    @Override
    public String serialize(Token token) {
        JWEHeader jweHeader = new JWEHeader.Builder(this.jweAlgorithm, this.encryptionMethod)
                .keyID(token.id().toString())
                .build();
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(token.id().toString())
                .subject(token.subject())
                .issueTime(Date.from(token.createdAt()))
                .expirationTime(Date.from(token.expiresAt()))
                .claim("authorities", token.authorities())
                .build();
        EncryptedJWT encryptedJWT = new EncryptedJWT(jweHeader, jwtClaimsSet);
        try {
            encryptedJWT.encrypt(this.jweEncrypter);
            return encryptedJWT.serialize();
        } catch (JOSEException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
