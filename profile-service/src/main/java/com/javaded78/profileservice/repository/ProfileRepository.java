package com.javaded78.profileservice.repository;

import com.javaded78.profileservice.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {

	Optional<Profile> findByEmail(String email);

	Page<Profile> findByUsernameContaining(String username, Pageable pageable);
}
