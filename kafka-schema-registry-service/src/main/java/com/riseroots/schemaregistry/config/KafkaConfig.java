package com.riseroots.schemaregistry.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

import com.riseroots.schemaregistry.SchemaRegistryServiceApplication;

@Configuration
public class KafkaConfig {

	private static final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);
	
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    @Lazy
    public void setKafkaTemplate(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Bean
    public NewTopic createTopic() {
        return TopicBuilder.name("customer-events")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
