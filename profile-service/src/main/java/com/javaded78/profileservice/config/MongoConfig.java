package com.javaded78.profileservice.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.lang.NonNull;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class MongoConfig extends AbstractMongoClientConfiguration {

	@Value("${spring.data.mongodb.database}")
	private String databaseName;

	@Bean
	MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}

	@Override
	@NonNull
	protected String getDatabaseName() {
		return databaseName;
	}

	@Override
	@NonNull
	public MongoClient mongoClient() {
		// Настройка клиента MongoDB с аутентификацией
		MongoCredential credential = MongoCredential.createCredential("mongo", "admin", "mongo".toCharArray());
		MongoClientSettings settings = MongoClientSettings.builder()
				.applyToClusterSettings(builder ->
						builder.hosts(Collections.singletonList(new ServerAddress("localhost", 27017))))
				.credential(credential)
				.build();

		return MongoClients.create(settings);
	}
}