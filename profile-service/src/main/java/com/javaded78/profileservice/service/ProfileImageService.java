package com.javaded78.profileservice.service;

import com.javaded78.profileservice.model.Profile;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.BiConsumer;
import java.util.function.Function;

public interface ProfileImageService {

	String getProfileImageByEmail(Function<Profile, String> typeProfileImage, String email);

	String uploadProfileImage(MultipartFile file,
	                           String loggedInUser,
	                           Function<Profile, String> getUrl,
	                           BiConsumer<Profile, String> setUrl
	);

	void deleteProfileImage(String loggedInUser,
	                    Function<Profile, String> getImageUrl,
	                    BiConsumer<Profile, String> setImageUrl
	);
}
