package com.javaded78.authenticationservice.service.impl;

import com.javaded78.authenticationservice.exception.ActivationCodeExpiredException;
import com.javaded78.authenticationservice.exception.ActivationCodeNotFoundException;
import com.javaded78.authenticationservice.model.ActivationCode;
import com.javaded78.authenticationservice.model.User;
import com.javaded78.authenticationservice.producer.SendRegistrationEmailProducer;
import com.javaded78.authenticationservice.repository.ActivationCodeRepository;
import com.javaded78.authenticationservice.service.ActivationCodeService;
import com.javaded78.authenticationservice.service.MessageSourceService;
import com.javaded78.commons.event.SendRegistrationCodeEmailEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivationCodeServiceImpl implements ActivationCodeService {

	private final SendRegistrationEmailProducer sendRegistrationEmailProducer;
	private final ActivationCodeRepository activationCodeRepository;
	private final MessageSourceService messageSourceService;

	@Transactional
	@Override
	public void sendNewActivationCode(User user) {
		ActivationCode activationCode = ActivationCode.builder()
				.user(user)
				.code(UUID.randomUUID().toString())
				.expirationTime(LocalDateTime.now().plusHours(2L))
				.build();
		SendRegistrationCodeEmailEvent sendRegistrationCodeEmailEvent = SendRegistrationEmailProducer.toSendRegistrationEmailEvent(
				user.getEmail(),
				user.getUsername(),
				activationCode.getCode()
		);
		activationCodeRepository.save(activationCode);
		sendRegistrationEmailProducer.sendRegistrationEmail(sendRegistrationCodeEmailEvent);
		log.info("ActivationCodeServiceImpl | sendNewActivationCode | activation message has been sent to {}", user.getEmail());
	}

	@Override
	public ActivationCode getActivationCode(String activationCode) {
		return activationCodeRepository.findActivationCodeByCode(activationCode)
				.orElseThrow(() -> new ActivationCodeNotFoundException(
						messageSourceService.generateMessage("error.activation_code.not_found", activationCode)
				));
	}

	@Override
	@Transactional
	public void checkActivationCodeExpiration(ActivationCode activationCode) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expirationTime = activationCode.getExpirationTime();
		if (now.isAfter(expirationTime)) {
			log.info("ActivationCodeServiceImpl | checkActivationCodeExpiration | activation message {} has expired",
					activationCode.getCode()
			);
			deleteActivationCode(activationCode.getId());
			sendNewActivationCode(activationCode.getUser());
			throw new ActivationCodeExpiredException(
					messageSourceService.generateMessage(
							"error.activation_code.expired",
							activationCode.getCode(),
							ChronoUnit.MINUTES.between(now, expirationTime),
							activationCode.getUser().getEmail()
					)
			);
		}
	}

	@Override
	@Transactional
	public void deleteActivationCode(Long id) {
		activationCodeRepository.deleteById(id);
	}
}
