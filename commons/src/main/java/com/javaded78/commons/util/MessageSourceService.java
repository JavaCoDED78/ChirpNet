package com.javaded78.commons.util;

public interface MessageSourceService {

    String generateMessage(String key);

    String generateMessage(String key, Object... args);
}
