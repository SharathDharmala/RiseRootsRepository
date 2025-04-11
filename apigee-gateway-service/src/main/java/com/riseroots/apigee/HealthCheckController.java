
package com.riseroots.apigee;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public String health() {
        return "Apigee Gateway Service is running";
    }
}
