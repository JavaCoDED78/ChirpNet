package com.javaded78.authenticationservice.repository;

import com.javaded78.authenticationservice.model.TokenUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenUserRepository extends JpaRepository<TokenUser, Long> {

    Optional<TokenUser> findByEmail(String email);

}
