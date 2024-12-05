package com.javaded78.profileservice.service.impl;

import com.javaded78.commons.util.MessageSourceServiceImpl;
import com.javaded78.profileservice.cache.constant.CacheConstant;
import com.javaded78.profileservice.dto.request.UpdateProfileRequest;
import com.javaded78.profileservice.dto.response.ProfileResponse;
import com.javaded78.profileservice.exception.ActionNotAllowedException;
import com.javaded78.profileservice.exception.EntityNotFoundException;
import com.javaded78.profileservice.mapper.ProfileMapper;
import com.javaded78.profileservice.model.Profile;
import com.javaded78.profileservice.service.ProfileImageService;
import com.javaded78.profileservice.service.ProfileService;
import com.javaded78.profileservice.service.ProfileSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultProfileSettingService implements ProfileSettingService {

	private final ProfileService profileService;
	private final ProfileImageService profileImageService;
	private final ProfileMapper profileMapper;
	private final MessageSourceServiceImpl messageSourceService;

	@Override
	public ProfileResponse updateProfile(String id, UpdateProfileRequest request, String loggedInUser) {
		return Optional.of(profileService.getProfileById(id))
				.filter(profile -> isUpdateAvailability(profile.getEmail(), loggedInUser))
				.map(profile -> profileMapper.fromUpdateProfileRequest(request, profile))
				.map(profileService::save)
				.map(profileMapper::toProfileResponse)
				.orElseThrow(() -> new EntityNotFoundException(
						messageSourceService.generateMessage("error.entity.not_found", id)
				));
	}

	private boolean isUpdateAvailability(String updatingUser, String loggedInUser) {
		if (!updatingUser.equals(loggedInUser)) {
			throw new ActionNotAllowedException(
					messageSourceService.generateMessage("error.forbidden")
			);
		}
		return true;
	}

	@Override
	public String uploadAvatarImage(MultipartFile file, String loggedInUser) {
		return profileImageService.uploadProfileImage(file, loggedInUser, Profile::getAvatarUrl, Profile::setAvatarUrl);
	}

	@Override
	public void deleteAvatarImage(String loggedInUser) {
		profileImageService.deleteProfileImage(loggedInUser, Profile::getAvatarUrl, Profile::setAvatarUrl);
	}

	@Override
	public String uploadBannerImage(MultipartFile file, String loggedInUser) {
		return profileImageService.uploadProfileImage(file, loggedInUser, Profile::getBannerUrl, Profile::setBannerUrl);
	}

	@Override
	public void deleteBannerImage(String loggedInUser) {
		profileImageService.deleteProfileImage(loggedInUser, Profile::getBannerUrl, Profile::setBannerUrl);
	}

	@Override
	@Cacheable(value = CacheConstant.GET_PROFILE_AVATAR_BY_EMAIL, key = "#loggedInUser")
	public String getAvatar(String loggedInUser) {
		return profileImageService.getProfileImageByEmail(Profile::getAvatarUrl, loggedInUser);
	}

	@Override
	@Cacheable(value = CacheConstant.GET_PROFILE_BANNER_BY_EMAIL, key = "#loggedInUser")
	public String getBanner(String loggedInUser) {
		return profileImageService.getProfileImageByEmail(Profile::getBannerUrl, loggedInUser);
	}
}
