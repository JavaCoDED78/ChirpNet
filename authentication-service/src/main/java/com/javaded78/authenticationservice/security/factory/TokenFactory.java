package com.javaded78.authenticationservice.security.factory;

import com.javaded78.authenticationservice.security.jwt.Token;

public interface TokenFactory <T, R extends Token>  {

    R create(T t);
}
