package com.me.auth_service.configuration;

import com.me.auth_service.configuration.jwt.JwtAuthenticationFilter;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                rq -> rq
                        .requestMatchers(HttpMethod.GET,
                                "/").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/auth/register",
                                "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/auth/logout").authenticated()

                        .requestMatchers(HttpMethod.GET,
                                "/user").hasRole("USER")
                        .requestMatchers(HttpMethod.GET,
                                "/admin").hasRole("ADMIN")

                        .anyRequest().authenticated()
        );

        http.oauth2ResourceServer(
                oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
        );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.cors(
                cors -> cors.disable()
        );

        http.csrf(
                csrf -> csrf.disable()
        );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return new NimbusJwtDecoder(new DefaultJWTProcessor<>());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
