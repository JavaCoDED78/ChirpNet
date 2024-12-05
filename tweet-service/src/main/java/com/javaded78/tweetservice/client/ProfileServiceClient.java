package com.javaded78.tweetservice.client;

import com.javaded78.tweetservice.config.FeignConfig;
import com.javaded78.tweetservice.dto.response.PaginationResponse;
import com.javaded78.tweetservice.dto.response.ProfileResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profile-service", url = "http://localhost:8082", configuration = FeignConfig.class)

public interface ProfileServiceClient {

	@GetMapping("/api/v1/profiles/id/{email}")
	@CircuitBreaker(name = "tweet-service", fallbackMethod = "fallbackGetProfileIdByLoggedInUser")
	String getProfileIdByLoggedInUser(@PathVariable String email);

	@GetMapping("/api/v1/profiles/{id}")
	@CircuitBreaker(name = "tweet-service", fallbackMethod = "fallbackGetProfileById")
	ProfileResponse getProfileById(@PathVariable String id);

	@GetMapping("/api/v1/follows/{profileId}/followers")
	@CircuitBreaker(name = "tweet-service", fallbackMethod = "fallbackGetFollowers")
	PaginationResponse<ProfileResponse> getFollowers(@PathVariable String profileId);

	default String fallbackGetProfileIdByLoggedInUser(String email, Throwable throwable) {
		return "";
	}

	default ProfileResponse fallbackGetProfileById(String id, Throwable throwable) {
		return null;
	}

	default PaginationResponse<ProfileResponse> fallbackGetFollowers(String profileId, Throwable throwable) {
		return null;
	}
}
