package com.javaded78.profileservice.service;

import com.javaded78.profileservice.dto.request.UpdateProfileRequest;
import com.javaded78.profileservice.dto.response.ProfileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileSettingService {

	ProfileResponse updateProfile(String id, UpdateProfileRequest request, String loggedInUser);

	Boolean uploadAvatarImage(MultipartFile file, String loggedInUser);

	Boolean deleteAvatarImage(String loggedInUser);

	Boolean uploadBannerImage(MultipartFile file, String loggedInUser);

	Boolean deleteBannerImage(String loggedInUser);
}
