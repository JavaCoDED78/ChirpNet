package com.javaded78.authenticationservice.service.impl;

import com.javaded78.authenticationservice.model.Role;
import com.javaded78.authenticationservice.model.TokenUser;
import com.javaded78.authenticationservice.repository.TokenUserRepository;
import com.javaded78.authenticationservice.service.TokenUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenUserServiceImpl implements TokenUserService {

    private final TokenUserRepository userRepository;

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    @Transactional
    public TokenUser createUser(String email, String password, boolean iaEnabled) {
        return userRepository.saveAndFlush(
                TokenUser.builder()
                        .email(email)
                        .password(password)
                        .isAccountNonLocked(true)
                        .isAccountNonExpired(true)
                        .isCredentialsNonExpired(true)
                        .isEnabled(iaEnabled)
                        .role(Role.ROLE_USER)
                        .build()
        );
    }

    @Override
    public void enableAccount(TokenUser user) {

    }
}
