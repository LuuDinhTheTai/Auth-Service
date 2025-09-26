package com.me.auth_service.configuration.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl extends InMemoryUserDetailsManager {

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder) {
        super();
        super.createUser(
                User
                        .withUsername("a")
                        .password(passwordEncoder.encode("a"))
                        .roles("ADMIN")
                        .build()
        );
        super.createUser(
                User
                        .withUsername("u")
                        .password(passwordEncoder.encode("u"))
                        .roles("USER")
                        .build()
        );
    }
}
