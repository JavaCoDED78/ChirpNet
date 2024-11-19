package com.javaded78.profileservice.mapper;

import com.javaded78.profileservice.dto.request.CreateProfileRequest;
import com.javaded78.profileservice.dto.response.ProfileResponse;
import com.javaded78.profileservice.model.Profile;
import com.javaded78.profileservice.service.FollowService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper extends Mappable<Profile, CreateProfileRequest> {

	@Mapping(target = "profileId", source = "id")
	@Mapping(target = "followerCount", expression = "java(followService.countFollowersForProfile(profile.getId()))")
	@Mapping(target = "followingCount", expression = "java(followService.countFollowingForProfile(profile.getId()))")
	ProfileResponse toProfileResponse(Profile profile, @Context FollowService followService);
}
