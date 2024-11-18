package com.javaded78.profileservice.service;

import com.javaded78.profileservice.dto.request.CreateProfileRequest;
import com.javaded78.profileservice.dto.request.UpdateProfileRequest;
import com.javaded78.profileservice.dto.response.ProfileResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfileService {

	String createProfile(CreateProfileRequest request);

	ProfileResponse getProfile(String id);

	ProfileResponse getAuthProfile(String loggedInUser);

	Page<ProfileResponse> getAllByUsername(String username, Pageable pageable);

	String getProfileIdByEmail(String email);

	String getProfileAvatar(String loggedInUser);

	String getProfileBanner(String loggedInUser);
}
