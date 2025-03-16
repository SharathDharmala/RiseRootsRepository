package com.riseroots.usermanagement.model;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Assuming you're using roles or authorities
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // Get password from User
    }

    @Override
    public String getUsername() {
        return user.getUsername(); // Get username from User
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement based on your needs
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement based on your needs
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement based on your needs
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
