
package com.riseroots.apigee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ApigeeGatewayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApigeeGatewayServiceApplication.class, args);
    }
}
