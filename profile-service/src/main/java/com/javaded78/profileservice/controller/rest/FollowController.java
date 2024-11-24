package com.javaded78.profileservice.controller.rest;

import com.javaded78.profileservice.dto.response.PaginationResponse;
import com.javaded78.profileservice.dto.response.ProfileResponse;
import com.javaded78.profileservice.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/follows")
@RequiredArgsConstructor
public class FollowController {

	private final FollowService followService;

	@GetMapping("/{followingId}")
	public ResponseEntity<Boolean> isFollowed(@PathVariable String followingId, @RequestHeader String loggedInUser) {
		return ResponseEntity.ok(followService.isFollowing(followingId, loggedInUser));
	}

	@PostMapping("/{followingId}")
	public ResponseEntity<Boolean> follow(@PathVariable String followingId, @RequestHeader String loggedInUser) {
		return ResponseEntity.ok(followService.follow(followingId, loggedInUser));
	}

	@DeleteMapping("/{followingId}")
	public ResponseEntity<Boolean> unfollow(@PathVariable String followingId, @RequestHeader String loggedInUser) {
		return ResponseEntity.ok(followService.unfollow(followingId, loggedInUser));
	}

	@GetMapping("{profileId}/followers")
	public ResponseEntity<PaginationResponse<ProfileResponse>> getFollowers(
			@PathVariable String profileId,
			Pageable pageable
	) {
		PaginationResponse<ProfileResponse> response = PaginationResponse
				.of(followService.getFollowers(profileId, pageable));
		return ResponseEntity.ok(response);
	}

	@GetMapping("{profileId}/followings")
	public ResponseEntity<PaginationResponse<ProfileResponse>> getFollowings(
			@PathVariable String profileId,
			Pageable pageable
	) {
		PaginationResponse<ProfileResponse> response = PaginationResponse
				.of(followService.getFollowings(profileId, pageable));
		return ResponseEntity.ok(response);
	}

	@GetMapping("{profileId}/followings-celebrity/{minFollowerCount}")
	public ResponseEntity<PaginationResponse<ProfileResponse>> getFollowingsCelebrity(
			@PathVariable String profileId,
			@PathVariable int minFollowerCount,
			Pageable pageable
	) {
		PaginationResponse<ProfileResponse> response = PaginationResponse
				.of(followService.getFollowingsCelebrity(profileId, minFollowerCount, pageable));
		return ResponseEntity.ok(response);
	}
}
