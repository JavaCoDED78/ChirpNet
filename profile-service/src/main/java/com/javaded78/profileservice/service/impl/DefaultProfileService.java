package com.javaded78.profileservice.service.impl;

import com.javaded78.profileservice.dto.request.CreateProfileRequest;
import com.javaded78.profileservice.service.ProfileService;
import org.springframework.stereotype.Service;

@Service
public class DefaultProfileService implements ProfileService {

	@Override
	public String createProfile(CreateProfileRequest request) {
		return "";
	}
}
