package com.javaded78.profileservice.controller.rest;

import com.javaded78.profileservice.dto.request.UpdateProfileRequest;
import com.javaded78.profileservice.dto.response.ProfileResponse;
import com.javaded78.profileservice.service.ProfileSettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/profiles")
@RequiredArgsConstructor
public class ProfileSettingController {

	private final ProfileSettingService profileSettingService;

	@PatchMapping("/{id}")
	public ResponseEntity<ProfileResponse> updateProfile(
			@Valid @RequestBody UpdateProfileRequest request,
			@PathVariable String id,
			@RequestHeader String loggedInUser
	) {
		return ResponseEntity.ok(profileSettingService.updateProfile(id, request, loggedInUser));
	}

	@PostMapping("/images/avatar")
	public ResponseEntity<Boolean> uploadAvatarImage(@RequestParam MultipartFile file, @RequestHeader String loggedInUser) {
		return ResponseEntity.ok(profileSettingService.uploadAvatarImage(file, loggedInUser));
	}

	@DeleteMapping("/images/avatar")
	public ResponseEntity<Boolean> deleteAvatarImage(@RequestHeader String loggedInUser) {
		return ResponseEntity.ok(profileSettingService.deleteAvatarImage(loggedInUser));
	}

	@PostMapping("/images/banner")
	public ResponseEntity<Boolean> uploadBannerImage(@RequestParam MultipartFile file, @RequestHeader String loggedInUser) {
		return ResponseEntity.ok(profileSettingService.uploadBannerImage(file, loggedInUser));
	}

	@DeleteMapping("/images/banner")
	public ResponseEntity<Boolean> deleteBannerImage(@RequestHeader String loggedInUser) {
		return ResponseEntity.ok(profileSettingService.deleteBannerImage(loggedInUser));
	}
}
