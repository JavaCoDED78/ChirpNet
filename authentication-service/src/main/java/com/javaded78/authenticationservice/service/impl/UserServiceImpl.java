package com.javaded78.authenticationservice.service.impl;

import com.javaded78.authenticationservice.dto.request.RegisterRequest;
import com.javaded78.authenticationservice.mapper.Mappable;
import com.javaded78.authenticationservice.model.Role;
import com.javaded78.authenticationservice.model.User;
import com.javaded78.authenticationservice.repository.UserRepository;
import com.javaded78.authenticationservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final Mappable<User, RegisterRequest> userMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	@Transactional
	public User createUser(RegisterRequest request) {
		User newUser = userMapper.toEntity(request);
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		Set<Role> roles = Set.of(Role.ROLE_USER);
		newUser.setAuthorities(roles);
		return userRepository.save(newUser);
	}

	@Override
	@Transactional
	public void enableAccount(User user) {
		user.setEnabled(true);
		userRepository.save(user);
	}

}
