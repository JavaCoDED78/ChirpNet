package com.javaded78.authenticationservice.client;

import com.javaded78.authenticationservice.dto.request.CreateProfileRequest;
import com.javaded78.authenticationservice.exception.UserProfileNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service", url = "http://localhost:8088")
public interface ProfileServiceClient {

    @PostMapping(value = "/api/v1/profiles")
    @CircuitBreaker(name = "profile-service", fallbackMethod = "fallbackMethod")
    String createProfile(@RequestBody CreateProfileRequest request);

//    default Throwable fallbackMethod(Throwable throwable) {
//        return new UserProfileNotFoundException(throwable);
//    }

    default String fallbackMethod(Throwable throwable) {
        return "123";
    }
}
