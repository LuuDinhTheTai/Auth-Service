package com.me.auth_service.service;

import com.me.auth_service.dto.request.LoginRequest;
import com.me.auth_service.dto.request.RegisterRequest;
import com.me.auth_service.dto.response.AccountResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    void register(RegisterRequest request);

    AccountResponse login(LoginRequest request, HttpServletResponse httpServletResponse);

    void logout();
}
