package com.javaded78.profileservice.service;

import com.javaded78.profileservice.dto.request.UpdateProfileRequest;
import com.javaded78.profileservice.dto.response.ProfileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileSettingService {

	ProfileResponse updateProfile(String id, UpdateProfileRequest request, String loggedInUser);

	String getAvatar(String loggedInUser);

	String getBanner(String loggedInUser);

	String uploadAvatarImage(MultipartFile file, String loggedInUser);

	void deleteAvatarImage(String loggedInUser);

	String uploadBannerImage(MultipartFile file, String loggedInUser);

	void deleteBannerImage(String loggedInUser);
}
