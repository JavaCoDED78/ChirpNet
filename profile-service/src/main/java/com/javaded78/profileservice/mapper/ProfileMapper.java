package com.javaded78.profileservice.mapper;

import com.javaded78.profileservice.dto.request.CreateProfileRequest;
import com.javaded78.profileservice.dto.request.UpdateProfileRequest;
import com.javaded78.profileservice.dto.response.ProfileResponse;
import com.javaded78.profileservice.model.Profile;
import com.javaded78.profileservice.service.FollowService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProfileMapper extends Mappable<Profile, CreateProfileRequest> {

	@Mapping(target = "profileId", source = "id")
	@Mapping(target = "followerCount", expression = "java(followService.countFollowersForProfile(profile.getId()))")
	@Mapping(target = "followingCount", expression = "java(followService.countFollowingForProfile(profile.getId()))")
	ProfileResponse toProfileResponse(Profile profile, @Context FollowService followService);


	@Mapping(target = "id", ignore = true)
	@Mapping(target = "email", ignore = true)
	@Mapping(target = "joinDate", ignore = true)
	Profile fromUpdateProfileRequest(UpdateProfileRequest updateProfileRequest, @MappingTarget Profile profile);
}
