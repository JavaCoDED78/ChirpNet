package com.javaded78.profileservice.exception;

public class ActionNotAllowedException extends RuntimeException{

    public ActionNotAllowedException(String message) {
        super(message);
    }

    public ActionNotAllowedException(Throwable cause) {
        super(cause);
    }
}
