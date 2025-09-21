package com.me.auth_service.service.impl;

import com.me.auth_service.dto.request.LoginRequest;
import com.me.auth_service.dto.request.RegisterRequest;
import com.me.auth_service.dto.response.AccountResponse;
import com.me.auth_service.entity.Account;
import com.me.auth_service.repository.AccountRepository;
import com.me.auth_service.repository.RoleRepository;
import com.me.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    @Override
    public void register(RegisterRequest request) {
        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setPassword(request.getPassword());
        accountRepository.save(account);
    }

    @Override
    public AccountResponse login(LoginRequest request) {
        return null;
    }

    @Override
    public void logout() {

    }
}
