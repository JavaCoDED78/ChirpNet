package com.javaded78.tweetservice.service;

import com.javaded78.tweetservice.dto.request.TweetCreateRequest;
import com.javaded78.tweetservice.dto.request.TweetUpdateRequest;
import com.javaded78.tweetservice.dto.response.PaginationResponse;
import com.javaded78.tweetservice.dto.response.TweetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface TweetService {

	TweetResponse createTweet(TweetCreateRequest tweetCreateRequest,
	                          MultipartFile[] files,
	                          String loggedInUser);

	TweetResponse createQuoteTweet(TweetCreateRequest tweetCreateRequest,
	                               MultipartFile[] files,
	                               String tweetId,
	                               String loggedInUser);

	TweetResponse getTweetResponseById(String tweetId,
	                                   String loggedInUser);

	Page<TweetResponse> getTweetResponsesForUser(String profileId,
	                                             Pageable pageable);

	TweetResponse updateTweet(TweetUpdateRequest tweetUpdateRequest,
	                          MultipartFile[] files,
	                          String tweetId,
	                          String loggedInUser);

	void deleteTweet(String tweetId,
	                 String loggedInUser);

//	Integer countRetweetsForTweet(String tweetId);
//
//	Integer countLikesForTweet(String tweetId);
}
