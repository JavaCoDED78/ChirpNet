package com.javaded78.tweetservice.model;

import java.io.Serializable;

public interface BaseEntity<E extends Serializable> {

    E getId();
}
