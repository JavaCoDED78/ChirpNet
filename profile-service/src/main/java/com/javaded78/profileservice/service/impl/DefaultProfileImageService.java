package com.javaded78.profileservice.service.impl;

import com.javaded78.profileservice.client.StorageServiceClient;
import com.javaded78.profileservice.model.Profile;
import com.javaded78.profileservice.service.ProfileImageService;
import com.javaded78.profileservice.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class DefaultProfileImageService implements ProfileImageService {

	private final ProfileService profileService;
	private final StorageServiceClient storageServiceClient;

	@Override
	public String getProfileImageByEmail(Function<Profile, String> typeProfileImage,
	                                     String email
	) {
		return Optional.of(profileService.getProfileByEmail(email))
				.map(typeProfileImage)
				.orElse("");
	}

	@Override
	public String uploadProfileImage(MultipartFile file,
	                                 String loggedInUser,
	                                 Function<Profile, String> getUrl,
	                                 BiConsumer<Profile, String> setUrl
	) {
		return Optional.of(profileService.getProfileByEmail(loggedInUser))
				.map(profile -> {
					deleteExistingImage(profile, getUrl);
					String url = storageServiceClient.uploadImage(file);
					setUrl.accept(profile, url);
					profileService.save(profile);
					return url;
				})
				.orElseThrow();
	}

	@Override
	public void deleteProfileImage(String loggedInUser,
	                               Function<Profile, String> getImageUrl,
	                               BiConsumer<Profile, String> setImageUrl
	) {
		Optional.of(profileService.getProfileByEmail(loggedInUser))
				.filter(profile -> {
					String existingUrl = getImageUrl.apply(profile);
					return existingUrl != null;
				})
				.map(profile -> {
					String existingUrl = getImageUrl.apply(profile);
					setImageUrl.accept(profile, "");
					profileService.save(profile);
					return storageServiceClient.deleteImage(existingUrl);
				})
				.orElseThrow();
	}

	private void deleteExistingImage(Profile profile, Function<Profile, String> getImageUrl) {
		String existingUrl = getImageUrl.apply(profile);
		if (existingUrl != null) {
			storageServiceClient.deleteImage(existingUrl);
		}
	}
}

