package com.javaded78.authenticationservice.service;

import com.javaded78.authenticationservice.model.TokenUser;

public interface ActivationCodeService {

    void sendActivationCode(TokenUser user);
}
