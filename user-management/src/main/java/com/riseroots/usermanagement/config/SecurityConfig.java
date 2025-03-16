package com.riseroots.usermanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.riseroots.usermanagement.security.JwtSecurityManager;

@Configuration
public class SecurityConfig {

	private final JwtSecurityManager jwtSecurityManager;

	public SecurityConfig(@Lazy JwtSecurityManager jwtSecurityManager) {
		this.jwtSecurityManager = jwtSecurityManager;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/login", "/register", "/api/user/login", "/api/user/register").permitAll() // Add "/api/user/login"
	            .requestMatchers("/actuator/**", "/eureka/**").permitAll()
	            .requestMatchers("/public/**").permitAll()
	            .anyRequest().authenticated()
	        )
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	    
	    http.addFilterBefore(jwtSecurityManager, UsernamePasswordAuthenticationFilter.class);
	    
	    return http.build();
	}

	@Bean(name = "scPasswordEncoder")
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
