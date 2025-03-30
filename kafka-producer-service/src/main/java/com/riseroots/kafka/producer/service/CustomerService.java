package com.riseroots.kafka.producer.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.riseroots.kafka.producer.CustomerEvent;
import com.riseroots.kafka.producer.repository.CustomerRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final KafkaProducerService kafkaProducerService;
	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

	@Scheduled(fixedRate = 60000)
	public void processCustomers() {
	    logger.info("🔥 processCustomers() started at: " + LocalDateTime.now());
	    List<CustomerEntity> customers = customerRepository.findAll();
	    logger.info("📌 Found {} customers", customers.size());

	    for (CustomerEntity entity : customers) {
	        logger.info("📢 Processing Customer: {}", entity.getId());

	        CustomerEvent customerAvroEvent = CustomerEvent.newBuilder()
	                .setId(entity.getId())
	                .setCustomername(entity.getCustomerName())
	                .setMobilenumber(entity.getMobileNumber())
	                .setSocialsecuritynumber(entity.getSocialSecurityNumber())
	                .setTaxnumber(entity.getTaxNumber())
	                .build();

	        logger.info("📦 Sending Kafka Event: {}", customerAvroEvent);
	        kafkaProducerService.sendMessage(customerAvroEvent);
	    }
	    logger.info("✅ processCustomers() completed at: " + LocalDateTime.now());
	}

}
