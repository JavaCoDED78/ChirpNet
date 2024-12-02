package com.javaded78.profileservice.repository;

import com.javaded78.profileservice.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomFollowRepository {

	Page<Profile> findFollowingsWithHighFollowers(String profileId, long minFollowers, Pageable pageable);
}
