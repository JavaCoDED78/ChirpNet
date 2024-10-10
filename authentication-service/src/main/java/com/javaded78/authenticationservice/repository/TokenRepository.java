package com.javaded78.authenticationservice.repository;

import com.javaded78.authenticationservice.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
}
