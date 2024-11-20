package com.javaded78.profileservice.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "storage-service", url = "http://localhost:8083")
public interface StorageServiceClient {

	@PostMapping(value = "/api/v1/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@CircuitBreaker(name = "storage-service", fallbackMethod = "fallbackUploadImage")
	String uploadImage(@RequestPart(value = "image") MultipartFile image);

	@GetMapping(value = "/api/v1/images")
	@CircuitBreaker(name = "storages-service", fallbackMethod = "fallbackDownloadImage")
	ByteArrayResource downloadImage(@RequestParam String url);

	@DeleteMapping(value = "/api/v1/images")
	@CircuitBreaker(name = "storage-service", fallbackMethod = "fallbackDeleteImage")
	Boolean deleteImage(@RequestParam String url);

	default String fallbackUploadImage(MultipartFile image) {
		return "";
	}

	default ByteArrayResource fallbackDownloadImage(String url) {
		return new ByteArrayResource(new byte[0]);
	}

	default Boolean fallbackDeleteImage(String url) {
		return false;
	}
}
