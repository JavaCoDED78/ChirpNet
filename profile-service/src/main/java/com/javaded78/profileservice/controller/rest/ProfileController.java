package com.javaded78.profileservice.controller.rest;

import com.javaded78.profileservice.dto.request.CreateProfileRequest;
import com.javaded78.profileservice.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileService profileService;

	@PostMapping
	public ResponseEntity<String> createProfile(@Valid @RequestBody CreateProfileRequest request) {
		return ResponseEntity.status(CREATED).body(profileService.createProfile(request));
	}
}
