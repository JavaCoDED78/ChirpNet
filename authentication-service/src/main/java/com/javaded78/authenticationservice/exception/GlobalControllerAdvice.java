package com.javaded78.authenticationservice.exception;

import com.javaded78.commons.util.MessageSourceService;
import com.nimbusds.jwt.proc.BadJWTException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

	private final MessageSourceService messageSourceService;

	@ExceptionHandler({EntityNotFoundException.class, ActivationCodeNotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionBody handleNotFoundException(Exception e) {
		return new ExceptionBody(e.getMessage());
	}

	@ExceptionHandler({EntityExistsException.class, BadCredentialsException.class, BadJWTException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionBody handleBadRequestException(Exception e) {
		return new ExceptionBody(e.getMessage());
	}

	@ExceptionHandler(ActivationCodeExpiredException.class)
	@ResponseStatus(HttpStatus.GONE)
	public ExceptionBody handleGoneException(Exception e) {
		return new ExceptionBody(e.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ExceptionBody handleForbiddenException(Exception e) {
		return new ExceptionBody(e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionBody handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		ExceptionBody exceptionBody = new ExceptionBody("Validation failed.");
		List<FieldError> errors = e.getBindingResult().getFieldErrors();
		exceptionBody.setErrors(errors.stream()
				.collect(Collectors.toMap(
						FieldError::getField,
						fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "",
						(existingMessage, newMessage) -> existingMessage + " " + newMessage)
				));
		return exceptionBody;
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionBody handleConstraintViolationException(ConstraintViolationException e) {
		ExceptionBody exceptionBody = new ExceptionBody("Validation failed.");
		exceptionBody.setErrors(e.getConstraintViolations().stream()
				.collect(toMap(
						violation -> violation.getPropertyPath().toString(),
						ConstraintViolation::getMessage
				)));
		return exceptionBody;
	}

	@ExceptionHandler({AuthenticationException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionBody handleAuthenticationException(AuthenticationException e) {
		return new ExceptionBody(
				messageSourceService.generateMessage("error.user.not_authenticated")
		);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionBody handleException(Exception e) {
		return new ExceptionBody(messageSourceService.generateMessage("error.internal_server_error"));
	}
}
