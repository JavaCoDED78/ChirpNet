package com.javaded78.authenticationservice.mapper;

import com.javaded78.authenticationservice.dto.request.RegisterRequest;
import com.javaded78.authenticationservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRegisteredMapper extends Mappable<User, RegisterRequest> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isEnabled", constant = "false")
    @Mapping(target = "isAccountNonExpired", constant = "true")
    @Mapping(target = "isAccountNonLocked", constant = "true")
    @Mapping(target = "isCredentialsNonExpired", constant = "true")
    @Mapping(target = "authorities", ignore = true)
    User toEntity(RegisterRequest dto);
}
