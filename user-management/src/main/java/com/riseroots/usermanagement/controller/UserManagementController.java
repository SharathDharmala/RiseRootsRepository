package com.riseroots.usermanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.riseroots.usermanagement.config.MessageConfigs;
import com.riseroots.usermanagement.model.CustomUserDetails;
import com.riseroots.usermanagement.model.TokenResponse;
import com.riseroots.usermanagement.model.User;
import com.riseroots.usermanagement.security.JwtSecurityManager;
import com.riseroots.usermanagement.service.UserService;
import com.riseroots.usermanagement.util.JwtUtil;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@RestController
@RequestMapping("${server.api.base-path}")
@CrossOrigin(origins = "${cors.allowed-origin}")
@RequiredArgsConstructor
public class UserManagementController {

	private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

	private final MessageConfigs messageConfig;
	private final UserService userService;
	private final JwtUtil jwtUtil;

	private final JwtSecurityManager jwtSecurityManager;

	@Value("${security.password-pattern}")
	private String passwordPattern;

	@PostMapping("/register")
	public String registerUser(@RequestBody User user) {
		boolean success = userService.registerUser(user);
		if (success) {
			return "User registered successfully!";
		}
		return "User registration failed!";
	}

	@PostMapping("/login")
	public ResponseEntity<String> authorizeUser(@RequestBody User userRequest) {
		UserDetails userDetails = new CustomUserDetails(userRequest);
		String userName = userDetails.getUsername();

		List<Optional<String>> validationMessages = List.of(validateUsername(userName),
				validatePassword(userRequest.getPassword()));

		List<String> errorMessages = validationMessages.stream().flatMap(Optional::stream).collect(Collectors.toList());

		if (!errorMessages.isEmpty()) {
			String errorMessage = String.join(" | ", errorMessages);
			return ResponseEntity.badRequest().body(messageConfig.getInvalidCreds() + errorMessage);
		}

		if (!userService.validateUser(userRequest)) {
			return ResponseEntity.badRequest().body(messageConfig.getInvalidCreds());
		}

//		String jwtToken =jwtUtil.generateToken(userName);
		String jwtToken = jwtSecurityManager.generateToken(userName);

		if (jwtToken == null || jwtToken.isEmpty()) {
			logger.error("JWT Token not generated for user: {}", userName);
		} else {
			logger.debug("jwtToken:: >> " + jwtToken);
		}
		return ResponseEntity.ok(jwtToken);
	}

	private Optional<String> validateUsername(String username) {
		return (username == null || username.isBlank()) ? Optional.of(messageConfig.getUsernameRequired())
				: Optional.empty();
	}

	private Optional<String> validatePassword(String password) {
		if (password == null) {
			return Optional.of(messageConfig.getPasswordRequired());
		}
		return Optional.empty();
	}

}