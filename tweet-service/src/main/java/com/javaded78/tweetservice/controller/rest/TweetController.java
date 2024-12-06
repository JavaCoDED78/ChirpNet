package com.javaded78.tweetservice.controller.rest;

import com.javaded78.tweetservice.dto.request.TweetCreateRequest;
import com.javaded78.tweetservice.dto.request.TweetUpdateRequest;
import com.javaded78.tweetservice.dto.response.PaginationResponse;
import com.javaded78.tweetservice.dto.response.TweetResponse;
import com.javaded78.tweetservice.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/tweets")
@RequiredArgsConstructor
public class TweetController {

	private final TweetService tweetService;

	@PostMapping
	public ResponseEntity<TweetResponse> createTweet(
			@Validated @RequestPart TweetCreateRequest tweetCreateRequest,
			@RequestPart(required = false) MultipartFile[] files,
			@RequestHeader String loggedInUser
	) {
		return ResponseEntity.ok(tweetService.createTweet(tweetCreateRequest, files, loggedInUser));
	}

	@PostMapping("/{tweetId}")
	public ResponseEntity<TweetResponse> createQuoteTweet(
			@Validated @RequestPart TweetCreateRequest tweetCreateRequest,
			@RequestPart(required = false) MultipartFile[] files,
			@PathVariable String tweetId,
			@RequestHeader String loggedInUser
	) {
		return ResponseEntity.ok(tweetService.createQuoteTweet(tweetCreateRequest, files, tweetId, loggedInUser));
	}

	@GetMapping("/{tweetId}")
	public ResponseEntity<TweetResponse> getTweet(
			@PathVariable String tweetId,
			@RequestHeader String loggedInUser
	) {
		return ResponseEntity.ok(tweetService.getTweetResponseById(tweetId, loggedInUser));
	}

	@GetMapping("/user/{profileId}")
	public ResponseEntity<PaginationResponse<TweetResponse>> getTweetsForUser(
			@PathVariable String profileId,
			Pageable pageable
	) {
		PaginationResponse<TweetResponse> response = PaginationResponse.of(tweetService.getTweetResponsesForUser(profileId, pageable));
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{tweetId}")
	public ResponseEntity<TweetResponse> updateTweet(
			@Validated @RequestPart TweetUpdateRequest tweetUpdateRequest,
			@RequestPart(required = false) MultipartFile[] files,
			@PathVariable String tweetId,
			@RequestHeader String loggedInUser
	) {
		return ResponseEntity.ok(tweetService.updateTweet(tweetUpdateRequest, files, tweetId, loggedInUser));
	}

	@DeleteMapping("/{tweetId}")
	public ResponseEntity<Void> deleteTweet(
			@PathVariable String tweetId,
			@RequestHeader String loggedInUser
	) {
		tweetService.deleteTweet(tweetId, loggedInUser);
		return ResponseEntity.noContent().build();
	}
}
