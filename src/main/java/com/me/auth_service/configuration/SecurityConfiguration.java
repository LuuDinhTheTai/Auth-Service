package com.me.auth_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

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

        http.httpBasic(
                Customizer.withDefaults()
        );

        http.cors(
                Customizer.withDefaults()
        );

        http.csrf(
                Customizer.withDefaults()
        );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("u")
                .password(passwordEncoder().encode("u"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("a")
                .password(passwordEncoder().encode("a"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
