package com.me.auth_service.service;

import com.me.auth_service.dto.request.LoginRequest;
import com.me.auth_service.dto.request.RegisterRequest;
import com.me.auth_service.dto.response.AccountResponse;

public interface AuthService {

    void register(RegisterRequest request);

    AccountResponse login(LoginRequest request);

    void logout();
}
