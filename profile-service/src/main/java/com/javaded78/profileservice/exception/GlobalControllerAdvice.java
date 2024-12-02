package com.javaded78.profileservice.exception;

import com.javaded78.commons.util.MessageSourceService;
import com.mongodb.MongoWriteException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

	@ExceptionHandler(MongoWriteException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionBody handleMongoWriteException(Exception e) {
		return new ExceptionBody(e.getMessage());
	}

	@ExceptionHandler(CreateEntityException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ExceptionBody handleCreateEntityException(Exception e) {
		return new ExceptionBody(e.getMessage());
	}

	@ExceptionHandler(ActionNotAllowedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ExceptionBody handleActionNotAllowedException(Exception e) {
		return new ExceptionBody(e.getMessage());
	}

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
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

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionBody handleException(Exception e) {
		return new ExceptionBody(messageSourceService.generateMessage("error.internal_server_error"));
	}
}
