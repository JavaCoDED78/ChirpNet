package com.javaded78.authenticationservice.service;

import com.javaded78.authenticationservice.dto.request.AuthRequest;
import com.javaded78.authenticationservice.dto.request.RegisterRequest;
import com.javaded78.authenticationservice.dto.response.ActivationCodeResponse;
import com.javaded78.authenticationservice.dto.response.AuthResponse;

public interface AuthenticationService {

    ActivationCodeResponse register(RegisterRequest request);

}
