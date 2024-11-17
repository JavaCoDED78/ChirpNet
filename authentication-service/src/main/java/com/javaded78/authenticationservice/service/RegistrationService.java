package com.javaded78.authenticationservice.service;

import com.javaded78.authenticationservice.dto.request.RegisterRequest;
import com.javaded78.authenticationservice.dto.response.ActivationCodeResponse;
import com.javaded78.authenticationservice.dto.response.ActivationResponse;

public interface RegistrationService {

    ActivationCodeResponse register(RegisterRequest request);

    ActivationResponse activate(String activationCode);
}
