package com.javaded78.storageservice.exception;

import com.javaded78.commons.util.MessageSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

	private final MessageSourceService messageSourceService;

	@ExceptionHandler(ImageUploadException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionBody handleUploadException(Exception e) {
		return new ExceptionBody(e.getMessage());
	}

	@ExceptionHandler({ImageDownloadException.class, ImageDeleteException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionBody handleDownloadAndDeleteException(Exception e) {
		return new ExceptionBody(e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionBody handleException(Exception e) {
		return new ExceptionBody(messageSourceService.generateMessage("error.internal_server_error"));
	}
}
