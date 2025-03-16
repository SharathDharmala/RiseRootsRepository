package com.riseroots.kafka.producer.service;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.riseroots.kafka.producer.CustomerEvent;
import com.riseroots.kafka.producer.repository.CustomerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final KafkaProducerService kafkaProducerService;

    @Scheduled(fixedRate = 60000) 
    public void processCustomers() {
        List<CustomerEntity> customers = customerRepository.findAll();
        
        for (CustomerEntity entity : customers) {
            CustomerEvent customerAvroEvent = CustomerEvent.newBuilder()
                    .setId(entity.getId())
                    .setCustomername(entity.getCustomerName())
                    .setMobilenumber(entity.getMobileNumber())
                    .setSocialsecuritynumber(entity.getSocialSecurityNumber())
                    .setTaxnumber(entity.getTaxNumber())
                    .build();

            kafkaProducerService.sendMessage(customerAvroEvent);
        }
    }
}
