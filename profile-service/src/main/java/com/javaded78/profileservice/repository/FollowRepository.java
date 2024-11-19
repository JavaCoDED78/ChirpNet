package com.javaded78.profileservice.repository;

import com.javaded78.profileservice.model.Follow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends MongoRepository<Follow, String> {

	@Query(value = "{ 'followerProfile.id': ?0 }", count = true)
	int countByFollowerProfileId(String profileId);

	@Query(value = "{ 'followeeProfile.id': ?0 }", count = true)
	int countByFolloweeProfileId(String profileId);
}
