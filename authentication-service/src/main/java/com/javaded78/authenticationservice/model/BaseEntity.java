package com.javaded78.authenticationservice.model;

import java.io.Serializable;

public interface BaseEntity <E extends Serializable> {

    E getId();
}
