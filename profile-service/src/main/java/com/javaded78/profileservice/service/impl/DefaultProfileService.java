package com.javaded78.profileservice.service.impl;

import com.javaded78.commons.util.MessageSourceService;
import com.javaded78.profileservice.cache.constant.CacheConstant;
import com.javaded78.profileservice.dto.request.CreateProfileRequest;
import com.javaded78.profileservice.dto.response.ProfileResponse;
import com.javaded78.profileservice.exception.CreateEntityException;
import com.javaded78.profileservice.exception.EntityNotFoundException;
import com.javaded78.profileservice.mapper.ProfileMapper;
import com.javaded78.profileservice.model.Profile;
import com.javaded78.profileservice.repository.ProfileRepository;
import com.javaded78.profileservice.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultProfileService implements ProfileService {

	private final ProfileRepository profileRepository;
	private final ProfileMapper profileMapper;
	private final MessageSourceService messageSourceService;

	@Override
	@Transactional
	public String createProfile(CreateProfileRequest request) {
		return Optional.of(request)
				.map(profileMapper::toEntity)
				.map(this::save)
				.map(Profile::getId)
				.orElseThrow(() -> new CreateEntityException(
						messageSourceService.generateMessage("error.unsuccessful_creation"))
				);
	}

	@Override
	@Cacheable(value = CacheConstant.GET_PROFILE_RESPONSE_BY_ID, key = "#id")
	public ProfileResponse getProfileResponseById(String id) {
		return profileMapper.toProfileResponse(getProfileById(id));
	}

	@Override
	@Cacheable(value = CacheConstant.GET_PROFILE_RESPONSE_BY_EMAIL, key = "#loggedInUser")
	public ProfileResponse getAuthProfileResponse(String loggedInUser) {
		return profileMapper.toProfileResponse(getProfileByEmail(loggedInUser));
	}

	@Override
	public Page<ProfileResponse> getAllByUsername(String username, Pageable pageable) {
		return profileRepository.findByUsernameContaining(username, pageable)
				.map(profileMapper::toProfileResponse);
	}

	@Override
	@Cacheable(value = CacheConstant.GET_PROFILE_ID_BY_EMAIL, key = "#email")
	public String getProfileIdByEmail(String email) {
		return getProfileByEmail(email).getId();
	}

	@Override
	@Cacheable(value = "ProfileService::getProfileAvatar", key = "#loggedInUser")
	public String getProfileAvatar(String loggedInUser) {
		return getProfileImage(Profile::getAvatarUrl, loggedInUser);
	}

	@Override
	@Cacheable(value = "ProfileService::getProfileBanner", key = "#loggedInUser")
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

	@Override
	public Profile getProfileById(String id) {
		return getProfile(() -> profileRepository.findById(id), id);
	}

	@Override
	public Profile getProfileByEmail(String email) {
		return getProfile(() -> profileRepository.findByEmail(email), email);
	}

	@Override
	@Transactional
	public Profile save(Profile profile) {
		return profileRepository.save(profile);
	}

	@Override
	public void saveAll(Profile... profiles) {
		profileRepository.saveAll(List.of(profiles));
	}

	private Profile getProfile(Supplier<Optional<Profile>> profileSupplier, String identifier) {
		return profileSupplier.get()
				.orElseThrow(() -> new EntityNotFoundException(
						messageSourceService.generateMessage("error.entity.not_found", identifier)
				));
	}
}
