package com.me.auth_service.configuration.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderImpl extends BCryptPasswordEncoder {
}
