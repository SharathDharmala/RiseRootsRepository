package com.riseroots.kafka.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import com.riseroots.kafka.producer.CustomerEvent;

@EnableKafka
public class KafkaConsumerService {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
	
	@KafkaListener(topics = "customer-events", groupId = "consumer-group-1", concurrency = "3")
	public void consume(CustomerEvent customerEvent) {
	    logger.debug("Consumer 1 consumed: " + customerEvent.toString());
	}
}