package com.javaded78.authenticationservice.controller.rest;

import com.javaded78.authenticationservice.dto.request.RegisterRequest;
import com.javaded78.authenticationservice.dto.response.ActivationCodeResponse;
import com.javaded78.authenticationservice.dto.response.ActivationResponse;
import com.javaded78.authenticationservice.service.RegistrationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RegistrationController {

    public static final String PATTERN_REQUEST_CODE = "^\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}$";
    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<ActivationCodeResponse> register(@Valid @RequestBody RegisterRequest request) {
        ActivationCodeResponse response = registrationService.register(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/activate")
    public ResponseEntity<ActivationResponse> activate(
            @RequestParam @Pattern(regexp = PATTERN_REQUEST_CODE, message = "{activation.invalid}")
            String activationCode
    ) {
        return ResponseEntity.ok(registrationService.activate(activationCode));
    }
}
