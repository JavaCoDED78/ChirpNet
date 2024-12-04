package com.javaded78.storageservice.config;

import com.javaded78.storageservice.config.props.MinioProperties;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MinioConfig {

	private final MinioProperties minioProperties;


	@Bean
	public MinioClient minioClient() {
		return MinioClient.builder()
				.endpoint(minioProperties.getUrl())
				.credentials(minioProperties.getAccessKey(),
						minioProperties.getSecretKey())
				.build();
	}
}