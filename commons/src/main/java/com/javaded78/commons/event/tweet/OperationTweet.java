package com.javaded78.commons.event.tweet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationTweet {

    ADD("ADD"),
    DELETE("DELETE");

    private final String operation;
}
