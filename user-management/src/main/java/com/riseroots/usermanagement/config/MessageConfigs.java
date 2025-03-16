package com.riseroots.usermanagement.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "messages")
@Data
public class MessageConfigs {
    private String invalidCreds;
	private String loginSuccess;
    private String usernameRequired;
    private String passwordRequired;
    private String passwordPatternError;

}
