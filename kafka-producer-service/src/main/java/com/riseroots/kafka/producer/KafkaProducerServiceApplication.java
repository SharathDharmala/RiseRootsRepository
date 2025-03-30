package com.riseroots.kafka.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaProducerServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerServiceApplication.class, args);
	}
}
