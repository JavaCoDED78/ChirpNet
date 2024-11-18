package com.javaded78.profileservice.mapper;

import com.javaded78.profileservice.dto.request.CreateProfileRequest;
import com.javaded78.profileservice.model.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper extends Mappable<Profile, CreateProfileRequest> {
}
