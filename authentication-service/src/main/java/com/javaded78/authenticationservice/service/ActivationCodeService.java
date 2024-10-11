package com.javaded78.authenticationservice.service;

import com.javaded78.authenticationservice.model.ActivationCode;
import com.javaded78.authenticationservice.model.TokenUser;

public interface ActivationCodeService {

    void sendNewActivationCode(TokenUser user);

    ActivationCode getActivationCode(String activationCode);

    void checkActivationCodeExpiration(ActivationCode activationCode);

    void deleteActivationCode(Long id);
}
