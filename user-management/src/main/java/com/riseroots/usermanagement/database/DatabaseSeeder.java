package com.riseroots.usermanagement.database;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.riseroots.usermanagement.model.User;
import com.riseroots.usermanagement.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DatabaseSeeder {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${user.initial-username}")
    private String username;

    @Value("${user.initial-password}")
    private String password;
    
    @PostConstruct
    public void seedDatabase() {
        if (userRepository.existsByEmail("default@example.com")) {
            return; 
        }

        userRepository.findByUsername(username).ifPresentOrElse(
            user -> System.out.println("User already exists, skipping seeding."),
            () -> {
                String hashedPassword = passwordEncoder.encode(password);
                User newUser = User.builder()
                    .username(username)
                    .password(hashedPassword)
                    .email("default@example.com")
                    .active(true)
                    .build();

                userRepository.save(newUser);
                System.out.println("Admin user seeded.");
            }
        );
    }

}

