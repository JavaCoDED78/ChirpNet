package com.javaded78.profileservice.service.impl;

import com.javaded78.commons.util.MessageSourceServiceImpl;
import com.javaded78.profileservice.client.StorageServiceClient;
import com.javaded78.profileservice.dto.request.UpdateProfileRequest;
import com.javaded78.profileservice.dto.response.ProfileResponse;
import com.javaded78.profileservice.exception.ActionNotAllowedException;
import com.javaded78.profileservice.exception.EntityNotFoundException;
import com.javaded78.profileservice.mapper.ProfileMapper;
import com.javaded78.profileservice.model.Profile;
import com.javaded78.profileservice.repository.ProfileRepository;
import com.javaded78.profileservice.service.ProfileSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.BiConsumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultProfileSettingService implements ProfileSettingService {

	private final ProfileRepository profileRepository;
	private final ProfileMapper profileMapper;
	private final MessageSourceServiceImpl messageSourceService;
	private final StorageServiceClient storageServiceClient;

	@Override
	@Transactional
	public ProfileResponse updateProfile(String id, UpdateProfileRequest request, String loggedInUser) {
		return profileRepository.findById(id)
				.filter(profile -> isUpdateAvailability(profile.getEmail(), loggedInUser))
				.map(profile -> profileMapper.fromUpdateProfileRequest(request, profile))
				.map(profileRepository::save)
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

	private Boolean uploadImage(MultipartFile file,
	                            String loggedInUser,
	                            Function<Profile, String> getUrl,
	                            BiConsumer<Profile, String> setUrl
	) {
		return profileRepository.findByEmail(loggedInUser)
				.map(profile -> {
					deleteExistingImage(profile, getUrl);
					String url = storageServiceClient.uploadImage(file);
					setUrl.accept(profile, url);
					profileRepository.save(profile);
					return url != null;
				})
				.orElse(false);
	}

	private Boolean deleteImage(String loggedInUser,
	                            Function<Profile, String> getUrl,
	                            BiConsumer<Profile, String> setUrl) {
		return profileRepository.findByEmail(loggedInUser)
				.filter(profile -> {
					String existingUrl = getUrl.apply(profile);
					return existingUrl != null;
				})
				.map(profile -> {
					String existingUrl = getUrl.apply(profile);
					setUrl.accept(profile, null);
					profileRepository.save(profile);
					return storageServiceClient.deleteImage(existingUrl);
				})
				.orElse(false);
	}


	private void deleteExistingImage(Profile profile, Function<Profile, String> getUrl) {
		String existingUrl = getUrl.apply(profile);
		if (existingUrl != null) {
			storageServiceClient.deleteImage(existingUrl);
		}
	}

	@Override
	@Transactional
	public Boolean uploadAvatarImage(MultipartFile file, String loggedInUser) {
		return uploadImage(file, loggedInUser, Profile::getAvatarUrl, Profile::setAvatarUrl);
	}

	@Override
	@Transactional
	public Boolean deleteAvatarImage(String loggedInUser) {
		return deleteImage(loggedInUser, Profile::getAvatarUrl, Profile::setAvatarUrl);
	}

	@Override
	@Transactional
	public Boolean uploadBannerImage(MultipartFile file, String loggedInUser) {
		return uploadImage(file, loggedInUser, Profile::getBannerUrl, Profile::setBannerUrl);
	}

	@Override
	@Transactional
	public Boolean deleteBannerImage(String loggedInUser) {
		return deleteImage(loggedInUser, Profile::getBannerUrl, Profile::setBannerUrl);
	}
}
