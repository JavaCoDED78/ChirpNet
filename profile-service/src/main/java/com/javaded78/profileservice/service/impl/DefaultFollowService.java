package com.javaded78.profileservice.service.impl;

import com.javaded78.commons.util.MessageSourceService;
import com.javaded78.profileservice.dto.response.ProfileResponse;
import com.javaded78.profileservice.exception.ActionNotAllowedException;
import com.javaded78.profileservice.exception.EntityNotFoundException;
import com.javaded78.profileservice.mapper.ProfileMapper;
import com.javaded78.profileservice.model.Follow;
import com.javaded78.profileservice.model.Profile;
import com.javaded78.profileservice.repository.FollowRepository;
import com.javaded78.profileservice.service.FollowService;
import com.javaded78.profileservice.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class DefaultFollowService implements FollowService {

	private final FollowRepository followRepository;
	private final ProfileService profileService;
	private final ProfileMapper profileMapper;
	private final MessageSourceService messageSourceService;

	@Override
	public Boolean isFollowing(String followingId, String loggedInUser) {
		try {
			Profile profile = profileService.getProfileByEmail(loggedInUser);
			return followRepository.doesFollowRelationshipExist(profile.getId(), followingId);
		} catch (EntityNotFoundException e) {
			throw new ActionNotAllowedException(
					messageSourceService.generateMessage("error.forbidden")
			);
		}

	}

	@Override
	public Boolean follow(String followingId, String loggedInUser) {
		Profile profile = profileService.getProfileByEmail(loggedInUser);
		if (!isFollowing(followingId, loggedInUser)) {
			Follow followEntity = createFollowEntity(profile.getId(), followingId);
			followRepository.save(followEntity);
			return true;
		}
		return false;
	}

	@Override
	public Boolean unfollow(String followingId, String loggedInUser) {
		if (!isFollowing(followingId, loggedInUser)) {
			return false;
		}
		Profile profileFollower = profileService.getProfileByEmail(loggedInUser);
		Profile profileFollowing = profileService.getProfileById(followingId);
		followRepository.removeFollowRelationship(profileFollower.getId(), followingId);
		unfollowCounts(profileFollower, profileFollowing);
		return true;
	}

	private void unfollowCounts(Profile profileFollower, Profile profileFollowing) {
		profileFollower.unSetFollowerCount();
		profileFollowing.unSetFollowingCount();
		profileService.saveAll(profileFollower, profileFollowing);
	}

	private Follow createFollowEntity(String followerId, String followeeId) {
		Profile profileFollower = profileService.getProfileById(followerId);
		Profile profileFollowing = profileService.getProfileById(followeeId);
		followCounts(profileFollower, profileFollowing);
		return Follow.builder()
				.followerProfile(profileFollower)
				.followeeProfile(profileFollowing)
				.followDateTime(LocalDateTime.now())
				.build();
	}

	private void followCounts(Profile profileFollower, Profile profileFollowing) {
		profileFollower.setFollowingCount();
		profileFollowing.setFollowerCount();
		profileService.saveAll(profileFollower, profileFollowing);
	}

	@Override
	public Page<ProfileResponse> getFollowers(String profileId, Pageable pageable) {
		return getProfilesFromFollows(
				() -> followRepository.findFollowersByFolloweeId(profileId, pageable),
				Follow::getFollowerProfile,
				pageable
		);
	}

	@Override
	public Page<ProfileResponse> getFollowings(String profileId, Pageable pageable) {
		return getProfilesFromFollows(
				() -> followRepository.findFollowingsByFollowerId(profileId, pageable),
				Follow::getFolloweeProfile,
				pageable
		);
	}

	@Override
	public Page<ProfileResponse> getFollowingsCelebrity(String profileId, int minFollowerCount, Pageable pageable) {
		Page<Profile> followers = followRepository.findFollowingsWithHighFollowers(profileId, minFollowerCount, pageable);
		List<ProfileResponse> profileResponses = followers.getContent()
				.stream()
				.map(profileMapper::toProfileResponse)
				.toList();
		return new PageImpl<>(profileResponses, pageable, followers.getTotalElements());
	}

	private Page<ProfileResponse> getProfilesFromFollows(
			Supplier<Page<Follow>> followSupplier,
			Function<Follow, Profile> profileExtractor,
			Pageable pageable
	) {
		Page<Follow> followPage = followSupplier.get();
		List<ProfileResponse> profileResponses = followPage.getContent()
				.stream()
				.map(profileExtractor)
				.map(profileMapper::toProfileResponse)
				.toList();
		return new PageImpl<>(profileResponses, pageable, followPage.getTotalElements());
	}
}
