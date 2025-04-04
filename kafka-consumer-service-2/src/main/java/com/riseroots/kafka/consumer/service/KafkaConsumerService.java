package com.riseroots.kafka.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import com.riseroots.kafka.producer.CustomerEvent;

@EnableKafka
public class KafkaConsumerService {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
	
	@KafkaListener(topics = "customer-events", groupId = "consumer-group-2")
	public void consume(CustomerEvent customerEvent) {
		// Business Logic for Consumer 1
		logger.debug("Consumer 2 consumed: " + customerEvent.toString());
		// Implement business logic (e.g., save data to a database)
	}
}
