package com.riseroots.usermanagement.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RefreshScope
@Component
@ConfigurationProperties(prefix = "spring.datasource")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class DatabaseConfig {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
}
