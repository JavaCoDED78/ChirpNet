package com.javaded78.authenticationservice.service;

import com.javaded78.authenticationservice.model.ActivationCode;
import com.javaded78.authenticationservice.model.User;

public interface ActivationCodeService {

    void sendNewActivationCode(User user);

    ActivationCode getActivationCode(String activationCode);

    void checkActivationCodeExpiration(ActivationCode activationCode);

    void deleteActivationCode(Long id);
}
