package com.javaded78.authenticationservice.config;

import com.javaded78.commons.event.SendRegistrationCodeEmailEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.javaded78.commons.constant.KafkaTopicConstants.SEND_EMAIL_TOPIC;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfiguration {

	private final Environment env;

	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("spring.kafka.producer.bootstrap-servers"));
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, env.getProperty("spring.kafka.producer.key-serializer"));
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, env.getProperty("spring.kafka.producer.value-serializer"));
		props.put(ProducerConfig.ACKS_CONFIG, env.getProperty("spring.kafka.producer.acks"));
		props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, env.getProperty("spring.kafka.producer.properties.delivery.timeout.ms"));
		props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, env.getProperty("spring.kafka.producer.properties.request.timeout.ms"));
		props.put(ProducerConfig.LINGER_MS_CONFIG, env.getProperty("spring.kafka.producer.properties.linger.ms"));
		props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, env.getProperty("spring.kafka.producer.properties.enable.idempotence"));
		return props;
	}

	@Bean
	public KafkaTemplate<String, SendRegistrationCodeEmailEvent> sendEmailEventKafkaTemplate() {
		return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfigs()));
	}

	@Bean
	NewTopic createSendEmailTopic() {
		return TopicBuilder.name(SEND_EMAIL_TOPIC).partitions(3).replicas(3).build();
	}
}
