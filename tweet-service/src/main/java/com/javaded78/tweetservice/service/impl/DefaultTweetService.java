package com.javaded78.tweetservice.service.impl;

import com.javaded78.commons.event.tweet.OperationTweet;
import com.javaded78.commons.event.tweet.TweetProcessingEvent;
import com.javaded78.commons.event.tweet.TypeTweet;
import com.javaded78.commons.util.MessageSourceService;
import com.javaded78.tweetservice.client.ProfileServiceClient;
import com.javaded78.tweetservice.dto.request.TweetCreateRequest;
import com.javaded78.tweetservice.dto.request.TweetUpdateRequest;
import com.javaded78.tweetservice.dto.response.ProfileResponse;
import com.javaded78.tweetservice.dto.response.TweetResponse;
import com.javaded78.tweetservice.exception.CreateEntityException;
import com.javaded78.tweetservice.exception.EntityNotFoundException;
import com.javaded78.tweetservice.mapper.TweetCreateMapper;
import com.javaded78.tweetservice.mapper.TweetMapper;
import com.javaded78.tweetservice.model.Tweet;
import com.javaded78.tweetservice.repository.TweetRepository;
import com.javaded78.tweetservice.service.MediaTweetService;
import com.javaded78.tweetservice.service.TweetService;
import com.javaded78.tweetservice.service.ViewService;
import com.javaded78.tweetservice.service.factory.EventFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultTweetService implements TweetService {

	private final TweetCreateMapper tweetCreateMapper;
	private final ProfileServiceClient profileServiceClient;
	private final TweetRepository tweetRepository;
	private final ApplicationEventPublisher applicationEventPublisher;
	private final MessageSourceService messageSourceService;
	private final TweetMapper tweetMapper;
	private final MediaTweetService mediaTweetService;
	private final EventFactory<TweetProcessingEvent> tweetProcessingEvent;
	private final ViewService viewService;

	@Override
	public TweetResponse createTweet(TweetCreateRequest tweetCreateRequest,
	                                 MultipartFile[] files,
	                                 String loggedInUser) {
		return Optional.of(tweetCreateRequest)
				.map(req -> tweetCreateMapper.toEntity(
						req,
						null,
						null,
						profileServiceClient,
						loggedInUser)
				)
				.map(tweet -> mediaTweetService.uploadMediaTweet(tweet, files))
				.map(tweetRepository::save)
				.map(tweet -> {
					applicationEventPublisher.publishEvent(
							tweetProcessingEvent.createEvent(
									tweet, TypeTweet.TWEETS, OperationTweet.ADD
							)
					);
					return tweetMapper.toTweetResponse(tweet, loggedInUser, this, profileServiceClient);
				})
				.orElseThrow(() -> new CreateEntityException(
						messageSourceService.generateMessage("error.entity.unsuccessful_creation")
				));
	}

	@Override
	public TweetResponse createQuoteTweet(TweetCreateRequest tweetCreateRequest,
	                                      MultipartFile[] files,
	                                      String tweetId,
	                                      String loggedInUser
	) {
		return tweetRepository.findById(UUID.fromString(tweetId))
				.map(quoteTweet -> tweetCreateMapper.toEntity(
						tweetCreateRequest,
						quoteTweet,
						null,
						profileServiceClient,
						loggedInUser
				))
				.map(tweet -> mediaTweetService.uploadMediaTweet(tweet, files))
				.map(tweetRepository::save)
				.map(tweet -> {
					applicationEventPublisher.publishEvent(
							tweetProcessingEvent.createEvent(
									tweet, TypeTweet.TWEETS, OperationTweet.ADD
							)
					);
					return tweetMapper.toTweetResponse(tweet, loggedInUser, this, profileServiceClient);
				})
				.orElseThrow(() -> new EntityNotFoundException(
						messageSourceService.generateMessage("error.entity.not_found", tweetId)
				));
	}

	@Override
	public TweetResponse getTweetResponseById(String tweetId, String loggedInUser) {
		return tweetRepository.findById(UUID.fromString(tweetId))
				.map(tweet -> {
					viewService.createView(tweet, loggedInUser, profileServiceClient);
					return tweet;
				})
				.map(tweet -> tweetMapper.toTweetResponse(tweet, loggedInUser, this, profileServiceClient))
				.orElseThrow(() -> new EntityNotFoundException(
						messageSourceService.generateMessage("error.entity.not_found", tweetId)
				));
	}

	@Override
	public Page<TweetResponse> getTweetResponsesForUser(String profileId, Pageable pageable) {
		ProfileResponse profileResponse = profileServiceClient.getProfileById(profileId);
		return tweetRepository.findAllWithReplyAndRetweetIsNull(profileId, pageable)
				.stream()
				.map(tweet -> tweetMapper.toTweetResponse(tweet, profileResponse.email(), this, profileServiceClient));
	}

	@Override
	public TweetResponse updateTweet(TweetUpdateRequest tweetUpdateRequest,
	                                 MultipartFile[] files,
	                                 String tweetId,
	                                 String loggedInUser
	) {
		String profileId = profileServiceClient.getProfileIdByLoggedInUser(loggedInUser);
		return tweetRepository.findOwnedById(tweetId, profileId)
				.map(tweet -> tweetMapper.updateTweet(tweetUpdateRequest, tweet))
				.map(tweet -> mediaTweetService.updateMediaTweet(tweet, files))
				.map(tweetRepository::save)
				.map(tweet -> tweetMapper.toTweetResponse(tweet, loggedInUser, this, profileServiceClient))
				.orElseThrow(() -> new EntityNotFoundException(
						messageSourceService.generateMessage("error.entity.not_found", tweetId)
				));
	}

	@Override
	public void deleteTweet(String tweetId, String loggedInUser) {
		String profileId = profileServiceClient.getProfileIdByLoggedInUser(loggedInUser);
		return tweetRepository.findOwnedById(tweetId, profileId)
				.map(tweet -> {
					applicationEventPublisher.publishEvent(
							tweetProcessingEvent.createEvent(
									tweet, TypeTweet.TWEETS, OperationTweet.ADD
							)
					);
					tweetRepository.delete(tweet);
				})
				.orElseThrow(() -> new EntityNotFoundException(
						messageSourceService.generateMessage("error.entity.not_found", tweetId)
				));
	}

//	@Override
//	public Integer countRetweetsForTweet(String tweetId) {
//		return tweetRepository.countRetweets((tweetId));
//	}
//
//	@Override
//	public Integer countLikesForTweet(String tweetId) {
//		return tweetRepository.countLikes((tweetId));
//	}
}
