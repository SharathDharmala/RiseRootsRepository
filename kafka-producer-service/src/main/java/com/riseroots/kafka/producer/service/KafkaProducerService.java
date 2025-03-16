package com.riseroots.kafka.producer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.riseroots.kafka.producer.CustomerEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, CustomerEvent> kafkaTemplate;
    
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    
    public void sendMessage(com.riseroots.kafka.producer.CustomerEvent customerAvro) {
        int partition = customerAvro.getId() % 3; // Example: Using ID to assign partition
        kafkaTemplate.send("customer-topic", partition, String.valueOf(customerAvro.getId()), customerAvro);
        logger.debug("Sent Customer Event: " + customerAvro + " to Partition: " + partition);
    }
}