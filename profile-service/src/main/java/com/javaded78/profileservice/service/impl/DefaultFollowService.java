package com.javaded78.profileservice.service.impl;

import com.javaded78.profileservice.repository.FollowRepository;
import com.javaded78.profileservice.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultFollowService implements FollowService {

	private final FollowRepository followRepository;
	@Override
	public int countFollowersForProfile(String profileId) {
		return followRepository.countByFollowerProfileId(profileId);
	}

	@Override
	public int countFollowingForProfile(String profileId) {
		return followRepository.countByFolloweeProfileId(profileId);
	}
}
