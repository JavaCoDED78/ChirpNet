package com.javaded78.profileservice.service;

import com.javaded78.profileservice.dto.request.CreateProfileRequest;
import com.javaded78.profileservice.dto.response.ProfileResponse;
import com.javaded78.profileservice.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfileService {

	String createProfile(CreateProfileRequest request);

	ProfileResponse getProfileResponseById(String id);

	ProfileResponse getAuthProfileResponse(String loggedInUser);

	Page<ProfileResponse> getAllByUsername(String username, Pageable pageable);

	String getProfileIdByEmail(String email);

	String getProfileAvatar(String loggedInUser);

	String getProfileBanner(String loggedInUser);

	Profile getProfileById(String id);

	Profile getProfileByEmail(String email);

	Profile save(Profile profile);

	void saveAll(Profile... profiles);
}
