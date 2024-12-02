package com.javaded78.profileservice.repository;

import com.javaded78.profileservice.model.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends MongoRepository<Follow, String>, CustomFollowRepository {

	@Query(value = "{ 'followerProfile.id': ?0, 'followeeProfile.id': ?1 }", exists = true)
	boolean doesFollowRelationshipExist(String followerId, String followeeId);

	@Query(value = "{ 'followerProfile.id': ?0, 'followeeProfile.id': ?1 }", delete = true)
	void removeFollowRelationship(String followerId, String followeeId);

	@Query(value = "{ 'followerProfile.id': ?0 }")
	Page<Follow> findFollowingsByFollowerId(String followerId, Pageable pageable);

	@Query(value = "{ 'followeeProfile.id': ?0 }")
	Page<Follow> findFollowersByFolloweeId(String followeeId, Pageable pageable);
}
