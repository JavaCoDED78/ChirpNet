package com.javaded78.profileservice.service;

public interface FollowService {

	public int countFollowersForProfile(String profileId);

	public int countFollowingForProfile(String profileId);
}
