package com.javaded78.profileservice.controller.rest;

import com.javaded78.profileservice.dto.request.UpdateProfileRequest;
import com.javaded78.profileservice.dto.response.PaginationResponse;
import com.javaded78.profileservice.dto.response.ProfileResponse;
import com.javaded78.profileservice.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileService profileService;

	@GetMapping("/{id}")
	public ResponseEntity<ProfileResponse> getProfile(@PathVariable String id) {
		return ResponseEntity.ok(profileService.getProfile(id));
	}

	@GetMapping("/me")
	public ResponseEntity<ProfileResponse> getAuthProfile(@RequestHeader String loggedInUser) {
		return ResponseEntity.ok(profileService.getAuthProfile(loggedInUser));
	}

	@GetMapping()
	public ResponseEntity<PaginationResponse<ProfileResponse>> findAllByUsername(
			@RequestParam String username,
			Pageable pageable
	) {
		PaginationResponse<ProfileResponse> response = PaginationResponse
				.of(profileService.getAllByUsername(username, pageable));
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ProfileResponse> updateProfile(
			@Valid @RequestBody UpdateProfileRequest request,
			@PathVariable String id,
			@RequestHeader String loggedInUser
	) {
		return ResponseEntity.ok(profileService.updateProfile(id, request, loggedInUser));
	}

	@GetMapping("/id/{email}")
	public ResponseEntity<String> getProfileIdByEmail(@PathVariable String email) {
		return ResponseEntity.ok(profileService.getProfileIdByEmail(email));
	}
}
