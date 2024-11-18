package com.javaded78.profileservice.service.impl;

import com.javaded78.commons.util.MessageSourceService;
import com.javaded78.profileservice.dto.request.CreateProfileRequest;
import com.javaded78.profileservice.mapper.ProfileMapper;
import com.javaded78.profileservice.model.Profile;
import com.javaded78.profileservice.repository.ProfileRepository;
import com.javaded78.profileservice.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultProfileService implements ProfileService {

	private final ProfileRepository profileRepository;
	private final ProfileMapper profileMapper;
	private final MessageSourceService messageSourceService;

	@Override
	public String createProfile(CreateProfileRequest request) {
		Profile newProfile = profileMapper.toEntity(request);
		Profile profile = profileRepository.save(newProfile);
		return  profile.getId();
	}
}
