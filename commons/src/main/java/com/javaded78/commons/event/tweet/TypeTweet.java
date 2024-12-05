package com.javaded78.commons.event.tweet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeTweet {

    TWEETS("tweets"),
    RETWEETS("retweets"),
    REPLIES("replies");

    private final String name;
}
