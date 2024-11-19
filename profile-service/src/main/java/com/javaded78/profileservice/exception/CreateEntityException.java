package com.javaded78.profileservice.exception;

public class CreateEntityException extends RuntimeException{

    public CreateEntityException(String message) {
        super(message);
    }

    public CreateEntityException(Throwable cause) {
        super(cause);
    }
}
