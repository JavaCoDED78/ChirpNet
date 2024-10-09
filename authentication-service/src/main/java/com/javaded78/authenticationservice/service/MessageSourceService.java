package com.javaded78.authenticationservice.service;

public interface MessageSourceService {

    String generateMessage(String key);

    String generateMessage(String key, Object... args);
}
