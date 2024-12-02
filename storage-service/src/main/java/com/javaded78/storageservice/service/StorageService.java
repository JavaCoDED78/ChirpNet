package com.javaded78.storageservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	String uploadImage(MultipartFile image);

	byte[] downloadImage(String url);

	Boolean deleteImage(String url);
}
