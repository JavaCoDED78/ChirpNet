package com.javaded78.commons.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class KafkaTopicConstants {

    public static final String SEND_EMAIL_TOPIC = "authentication-service.user.send-email";
    public static final String USER_TIMELINE_TOPIC = "tweet-service.user.timeline";
    public static final String HOME_TIMELINE_TOPIC = "tweet-service.home.timeline";
}
