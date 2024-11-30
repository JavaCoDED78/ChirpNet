//package com.javaded78.profileservice.config;
//
//import com.mongodb.MongoClientSettings;
//import com.mongodb.MongoCredential;
//import com.mongodb.ServerAddress;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.env.Environment;
//import org.springframework.data.mongodb.MongoDatabaseFactory;
//import org.springframework.data.mongodb.MongoTransactionManager;
//import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
//import org.springframework.data.mongodb.gridfs.GridFsTemplate;
//import org.springframework.lang.NonNull;
//
//import java.util.Collections;
//import java.util.List;
//
//@Configuration
//@RequiredArgsConstructor
//public class MongoConfig extends AbstractMongoClientConfiguration {
//
////	@Value("${spring.data.mongodb.database}")
////	private String databaseName;
//
//	private final Environment env;
//
//
//	@Bean
//	MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
//		return new MongoTransactionManager(mongoDatabaseFactory);
//	}
//
////	@Bean
////	@Primary
////	public MongoClient mongoClient() {
////		return MongoClients.create("mongodb://admin:mongo@mongo1:27017,mongo2:27018,mongo3:27019/profile-service?replicaSet=rs0&authSource=admin");
////	}
//
//	@Bean
//	public MongoTemplate mongoTemplate() {
//		return new MongoTemplate(mongoClient(), "profile-service");
//	}
//
////	@Bean
////	MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
////		return new MongoTransactionManager(mongoDatabaseFactory);
////	}
////
//////	@Bean
//////	public GridFsTemplate gridFsTemplate(MongoDatabaseFactory mongoDatabaseFactory, MappingMongoConverter mongoConverter) {
//////		return new GridFsTemplate(mongoDatabaseFactory, mongoConverter);
//////	}
////
//	@Override
//	@NonNull
//	protected String getDatabaseName() {
//		return "profile-service";
//	}
////
//////	@Override
//////	@NonNull
//////	public MongoClient mongoClient() {
//////		// Настройка клиента MongoDB с аутентификацией
//////		MongoCredential credential = MongoCredential.createCredential("profile", "profile-service", "profile".toCharArray());
//////		MongoClientSettings settings = MongoClientSettings.builder()
//////				.applyToClusterSettings(builder ->
//////						builder.hosts(List.of(
//////								new ServerAddress("mongo1", 27017),
//////								new ServerAddress("mongo2", 27018),
//////								new ServerAddress("mongo3", 27019))))
//////
//////				.credential(credential)
//////				.build();
//////
//////		return MongoClients.create(settings);
//////	}
//}