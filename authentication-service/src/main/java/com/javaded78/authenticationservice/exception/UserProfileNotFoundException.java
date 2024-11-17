package com.javaded78.authenticationservice.exception;

public class UserProfileNotFoundException extends RuntimeException {
	public UserProfileNotFoundException(String message) {
		super(message);
	}

	public UserProfileNotFoundException(Throwable cause) {
		super(cause);
	}
}
