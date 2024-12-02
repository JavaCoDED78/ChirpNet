package com.javaded78.profileservice.service;

import com.javaded78.profileservice.dto.response.ProfileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FollowService {

	Boolean isFollowing(String followingId, String loggedInUser);

	Boolean follow(String followingId, String loggedInUser);

	Boolean unfollow(String followingId, String loggedInUser);

	Page<ProfileResponse> getFollowers(String profileId, Pageable pageable);

	Page<ProfileResponse> getFollowings(String profileId, Pageable pageable);

	Page<ProfileResponse> getFollowingsCelebrity(String profileId, int minFollowerCount, Pageable pageable);
}
