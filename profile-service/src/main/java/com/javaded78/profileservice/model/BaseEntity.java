package com.javaded78.profileservice.model;

import java.io.Serializable;

public interface BaseEntity<E extends Serializable> {

    E getId();
}
