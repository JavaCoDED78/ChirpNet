package com.javaded78.tweetservice.service.impl;

import com.javaded78.tweetservice.client.ProfileServiceClient;
import com.javaded78.tweetservice.dto.request.TweetCreateRequest;
import com.javaded78.tweetservice.dto.request.TweetUpdateRequest;
import com.javaded78.tweetservice.dto.response.PaginationResponse;
import com.javaded78.tweetservice.dto.response.TweetResponse;
import com.javaded78.tweetservice.mapper.TweetCreateMapper;
import com.javaded78.tweetservice.repository.TweetRepository;
import com.javaded78.tweetservice.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultTweetService implements TweetService {

	private final TweetCreateMapper tweetCreateMapper;
	private final ProfileServiceClient profileServiceClient;
	private final TweetRepository tweetRepository;

	@Override
	public TweetResponse createTweet(TweetCreateRequest tweetCreateRequest,
	                                 MultipartFile[] files,
	                                 String loggedInUser) {
		return Optional.of(tweetCreateRequest)
				.map(req -> tweetCreateMapper.toEntity(req, null, null, profileServiceClient, loggedInUser))
				.map(tweetRepository::save)
				.map(tweet -> );
	}

	@Override
	public TweetResponse createQuoteTweet(TweetCreateRequest tweetCreateRequest, MultipartFile[] files, String tweetId, String loggedInUser) {
		return null;
	}

	@Override
	public TweetResponse getTweetResponseById(String tweetId, String loggedInUser) {
		return null;
	}

	@Override
	public PaginationResponse<TweetResponse> getTweetResponsesForUser(String profileId, Pageable pageable) {
		return null;
	}

	@Override
	public TweetResponse updateTweet(TweetUpdateRequest tweetUpdateRequest, MultipartFile[] files, String tweetId, String loggedInUser) {
		return null;
	}

	@Override
	public void deleteTweet(String tweetId, String loggedInUser) {

	}
}
