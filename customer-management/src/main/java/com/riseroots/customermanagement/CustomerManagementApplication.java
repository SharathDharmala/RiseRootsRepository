package com.riseroots.customermanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class CustomerManagementApplication {
    private static final Logger logger = LoggerFactory.getLogger(CustomerManagementApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CustomerManagementApplication.class, args);
        logger.info("Customer Management Service Started");
    }
}