package com.javaded78.storageservice.service.impl;

import com.javaded78.commons.util.MessageSourceService;
import com.javaded78.storageservice.config.props.MinioProperties;
import com.javaded78.storageservice.exception.ImageDeleteException;
import com.javaded78.storageservice.exception.ImageDownloadException;
import com.javaded78.storageservice.exception.ImageUploadException;
import com.javaded78.storageservice.service.StorageService;
import io.minio.BucketExistsArgs;
import io.minio.DeleteObjectTagsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultStorageService implements StorageService {

	private final MinioClient minioClient;
	private final MinioProperties minioProperties;
	private final MessageSourceService messageSourceService;

	@Override
	public String uploadImage(MultipartFile image) {
		try {
			createBucket();
		} catch (Exception e) {
			throw new ImageUploadException(
					messageSourceService.generateMessage("error.image.upload_failed", e.getMessage())
			);
		}
		if (image.isEmpty() || image.getOriginalFilename() == null) {
			throw new ImageUploadException(
					messageSourceService.generateMessage("error.image.is_empty")
			);
		}
		String fileName = generateFileName(image);
		try (InputStream inputStream = image.getInputStream()) {
			saveImage(inputStream, fileName);
		} catch (Exception e) {
			throw new ImageUploadException(
					messageSourceService.generateMessage("error.image.upload_failed", e.getMessage())
			);
		}
		return fileName;
	}

	@SneakyThrows
	private void saveImage(InputStream inputStream, String fileName) {
		minioClient.putObject(PutObjectArgs.builder()
				.stream(inputStream, inputStream.available(), -1)
				.bucket(minioProperties.getBucket())
				.object(fileName)
				.build());
	}

	private String generateFileName(MultipartFile image) {
		String extension = getExtension(image);
		return UUID.randomUUID() + "." + extension;
	}

	private String getExtension(MultipartFile image) {
		return Objects.requireNonNull(image.getOriginalFilename())
				.substring(image.getOriginalFilename().lastIndexOf(".") + 1);
	}

	@SneakyThrows
	private void createBucket() {
		boolean exists = minioClient.bucketExists(
				BucketExistsArgs.builder()
						.bucket(minioProperties.getBucket())
						.build()
		);
		if (!exists) {
			minioClient.makeBucket(
					MakeBucketArgs.builder()
							.bucket(minioProperties.getBucket())
							.build()
			);
		}
	}

	@Override
	public byte[] downloadImage(String url) {
		try (InputStream inputStream = getObject(url)) {
			return inputStream.readAllBytes();
		} catch (Exception e) {
			throw new ImageDownloadException(
					messageSourceService.generateMessage("error.image.download_failed", e.getMessage())
			);
		}
	}

	@SneakyThrows
	private GetObjectResponse getObject(String url) {
		return minioClient.getObject(GetObjectArgs.builder()
				.bucket(minioProperties.getBucket())
				.object(url)
				.build());
	}

	@Override
	public Boolean deleteImage(String url) {
		try {
			minioClient.deleteObjectTags(
					DeleteObjectTagsArgs.builder()
							.bucket(minioProperties.getBucket())
							.object(url)
							.build()
			);
			return true;
		} catch (Exception e) {
			throw new ImageDeleteException(
					messageSourceService.generateMessage("error.image.delete_failed", e.getMessage())
			);
		}
	}
}
