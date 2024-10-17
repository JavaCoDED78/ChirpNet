package com.javaded78.authenticationservice.client;

import com.javaded78.authenticationservice.dto.request.CreateProfileRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service", url = "http://localhost:8088")
public interface ProfileServiceClient {

    @PostMapping(value = "/api/v1/profiles")
    String createProfile(@RequestBody CreateProfileRequest request);
}
