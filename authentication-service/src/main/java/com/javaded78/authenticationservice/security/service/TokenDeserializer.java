package com.javaded78.authenticationservice.security.service;

import com.javaded78.authenticationservice.security.jwt.Token;

public interface TokenDeserializer<F, T extends Token> {

    T deserialize(F from);
}
