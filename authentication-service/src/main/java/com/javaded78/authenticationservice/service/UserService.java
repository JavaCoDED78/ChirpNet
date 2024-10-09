package com.javaded78.authenticationservice.service;

import com.javaded78.authenticationservice.model.TokenUser;

public interface UserService {

    boolean existsByEmail(String email);

    TokenUser createUser(String email, String password, boolean iaEnabled);
}
