package com.javaded78.authenticationservice.service.impl;

import com.javaded78.authenticationservice.client.ProfileServiceClient;
import com.javaded78.authenticationservice.dto.request.CreateProfileRequest;
import com.javaded78.authenticationservice.dto.request.RegisterRequest;
import com.javaded78.authenticationservice.dto.response.ActivationCodeResponse;
import com.javaded78.authenticationservice.model.TokenUser;
import com.javaded78.authenticationservice.service.ActivationCodeService;
import com.javaded78.authenticationservice.service.AuthenticationService;
import com.javaded78.authenticationservice.service.MessageSourceService;
import com.javaded78.authenticationservice.service.UserService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final MessageSourceService messageService;
    private final ProfileServiceClient profileServiceClient;
    private final ActivationCodeService activationCodeService;

    @Override
    @Transactional
    public ActivationCodeResponse register(RegisterRequest request) {
        if (userService.existsByEmail(request.email())) {
            throw new EntityExistsException(
                    messageService.generateMessage("error.account.already_exists", request.email())
            );
        }

        TokenUser newUser = userService.createUser(request.email(), request.username(), false);
        log.info("AuthenticationServiceImpl | register | new user : {} has been created", newUser);

        CreateProfileRequest createProfileRequest = new CreateProfileRequest(
                request.username(), request.email(), LocalDate.now()
        );
        String profileId = profileServiceClient.createProfile(createProfileRequest);
        log.info("AuthenticationServiceImpl | register | new profile with id: {} has been created", profileId);

        activationCodeService.sendActivationCode(newUser);
        log.info("AuthenticationServiceImpl | register | activation code has been sent to {}", request.email());

        return new ActivationCodeResponse(messageService.generateMessage("activation.send.success"));
    }
}
