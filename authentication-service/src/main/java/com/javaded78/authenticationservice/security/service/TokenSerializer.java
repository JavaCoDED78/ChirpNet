package com.javaded78.authenticationservice.security.service;

import com.javaded78.authenticationservice.security.jwt.Token;

public interface TokenSerializer <F extends Token, T> {

    T serialize(F from);
}
