package com.javaded78.profileservice.service.impl;

import com.javaded78.commons.util.MessageSourceService;
import com.javaded78.profileservice.dto.request.CreateProfileRequest;
import com.javaded78.profileservice.dto.response.ProfileResponse;
import com.javaded78.profileservice.exception.CreateEntityException;
import com.javaded78.profileservice.exception.EntityNotFoundException;
import com.javaded78.profileservice.mapper.ProfileMapper;
import com.javaded78.profileservice.model.Profile;
import com.javaded78.profileservice.repository.ProfileRepository;
import com.javaded78.profileservice.service.FollowService;
import com.javaded78.profileservice.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class DefaultProfileService implements ProfileService {

	private final ProfileRepository profileRepository;
	private final ProfileMapper profileMapper;
	private final MessageSourceService messageSourceService;
	private final FollowService followService;

	@Override
	public String createProfile(CreateProfileRequest request) {
		return Optional.of(request)
				.map(profileMapper::toEntity)
				.map(profileRepository::save)
				.map(Profile::getId)
				.orElseThrow(() -> new CreateEntityException(
						messageSourceService.generateMessage("error.unsuccessful_creation"))
				);
	}

	@Override
	public ProfileResponse getProfile(String id) {
		return getProfileBy(
				() -> profileRepository.findById(id),
				id
		);
	}

	@Override
	public ProfileResponse getAuthProfile(String loggedInUser) {
		return getProfileBy(
				() -> profileRepository.findByEmail(loggedInUser),
				loggedInUser
		);
	}

	private ProfileResponse getProfileBy(Supplier<Optional<Profile>> profileSupplier, String identifier) {
		return profileSupplier.get()
				.map(profile -> profileMapper.toProfileResponse(profile, followService))
				.orElseThrow(() -> new EntityNotFoundException(
						messageSourceService.generateMessage("error.entity.not_found", identifier)
				));
	}

	@Override
	public Page<ProfileResponse> getAllByUsername(String username, Pageable pageable) {
		return profileRepository.findByUsernameContaining(username, pageable)
				.map(profile -> profileMapper.toProfileResponse(profile, followService));
	}

	@Override
	public String getProfileIdByEmail(String email) {
		return profileRepository.findByEmail(email)
				.map(Profile::getId)
				.orElseThrow(() -> new EntityNotFoundException(
						messageSourceService.generateMessage("error.entity.not_found", email)
				));
	}

	@Override
	public String getProfileAvatar(String loggedInUser) {
		return getProfileImage(Profile::getAvatarUrl, loggedInUser);
	}

	@Override
	public String getProfileBanner(String loggedInUser) {
		return getProfileImage(Profile::getBannerUrl, loggedInUser);
	}

	private String getProfileImage(Function<Profile, String> function, String email) {
		return profileRepository.findByEmail(email)
				.map(function)
				.orElseThrow(() -> new EntityNotFoundException(
						messageSourceService.generateMessage("error.image.not_found", email)
				));
	}
}
