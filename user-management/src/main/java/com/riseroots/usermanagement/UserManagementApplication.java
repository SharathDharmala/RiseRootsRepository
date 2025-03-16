package com.riseroots.usermanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "com.riseroots.usermanagement")
@EntityScan(basePackages = "com.riseroots.usermanagement.model")
@EnableJpaRepositories(basePackages = "com.riseroots.usermanagement.repository")
@ComponentScan(basePackages = "com.riseroots.usermanagement")
public class UserManagementApplication {
    private static final Logger logger = LoggerFactory.getLogger(UserManagementApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(UserManagementApplication.class, args);
        logger.info("User Management Service Started");
    }
}
	