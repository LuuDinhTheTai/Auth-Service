package com.me.auth_service.service.impl;

import com.me.auth_service.configuration.impl.UserDetailsServiceImpl;
import com.me.auth_service.dto.request.LoginRequest;
import com.me.auth_service.dto.request.RegisterRequest;
import com.me.auth_service.dto.response.AccountResponse;
import com.me.auth_service.entity.Account;
import com.me.auth_service.repository.AccountRepository;
import com.me.auth_service.repository.RoleRepository;
import com.me.auth_service.service.AuthService;
import com.me.auth_service.service.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenService jwtService;

    @Override
    public void register(RegisterRequest request) {
        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setPassword(request.getPassword());
        accountRepository.save(account);
    }

    @Override
    public AccountResponse login(
            LoginRequest request,
            HttpServletResponse httpServletResponse
    ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var account = userDetailsService.loadUserByUsername(request.getUsername());

        var jwtToken = jwtService.generateToken(account);

        httpServletResponse.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);

        return AccountResponse.builder()
                .build();
    }

    @Override
    public void logout() {

    }
}
