package com.javaded78.authenticationservice.repository;

import com.javaded78.authenticationservice.model.ActivationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivationCodeRepository extends JpaRepository<ActivationCode, Long> {

    Optional<ActivationCode> findActivationCodeByCode(String code);

    Optional<ActivationCode> findActivationCodeByUser_Email(String email);
}
