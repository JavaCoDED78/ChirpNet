package com.javaded78.authenticationservice.service;

import com.javaded78.authenticationservice.dto.request.RegisterRequest;
import com.javaded78.authenticationservice.model.User;

public interface UserService {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    User createUser(RegisterRequest request);

    void enableAccount(User user);
}
