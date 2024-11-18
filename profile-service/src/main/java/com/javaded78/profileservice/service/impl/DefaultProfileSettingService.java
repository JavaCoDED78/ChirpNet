package com.javaded78.profileservice.service.impl;

import com.javaded78.profileservice.dto.request.UpdateProfileRequest;
import com.javaded78.profileservice.dto.response.ProfileResponse;
import com.javaded78.profileservice.service.ProfileSettingService;
import org.springframework.stereotype.Service;

@Service
public class DefaultProfileSettingService implements ProfileSettingService {
	@Override
	public ProfileResponse updateProfile(String id, UpdateProfileRequest request, String loggedInUser) {
		return null;
	}
}
