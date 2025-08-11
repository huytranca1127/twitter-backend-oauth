package com.example.twitterbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable()) // disable CSRF for simplicity
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/error", "/oauth2/**", "/token", "/auth/**").permitAll()
            .anyRequest().permitAll() // manual validation inside controller
        )
        .oauth2Login(oauth2 -> oauth2
            .defaultSuccessUrl("/token", true)
        );
    return http.build();
}
}
