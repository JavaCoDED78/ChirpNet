package com.javaded78.authenticationservice.exception;

public class ActivationCodeExpiredException extends RuntimeException {

    public ActivationCodeExpiredException(String message) {
        super(message);
    }
}
