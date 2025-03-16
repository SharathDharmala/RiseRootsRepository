package com.riseroots.usermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.riseroots.usermanagement.model.User;
import com.riseroots.usermanagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Inject the specific PasswordEncoder via Qualifier (scPasswordEncoder)
    @Autowired
    @Qualifier("scPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(User userParam) {
        if (userRepository.existsByUsername(userParam.getUsername())) {
            return false; // User already exists
        }

        User user = new User();
        user.setUsername(userParam.getUsername());
        user.setPassword(passwordEncoder.encode(userParam.getPassword()));
        user.setEmail(userParam.getEmail());
        user.setActive(userParam.isActive());
        userRepository.save(user);
        return true;
    }

    public boolean validateUser(User userParam) {
        User user = userRepository.findByUsername(userParam.getUsername()).orElse(null);
        return passwordEncoder.matches(userParam.getPassword(), user.getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
