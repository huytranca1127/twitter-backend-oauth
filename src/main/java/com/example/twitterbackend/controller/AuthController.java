package com.example.twitterbackend.controller;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.twitterbackend.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token) {
    return userRepository.findByToken(token)
        .map(user -> {
            Instant exp = user.getTokenExpiry();
            if (exp == null || Instant.now().isAfter(exp)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Token expired"));
            }

            // Robust role handling
            String role = user.getRole();
            // Accept only Producer / Subscriber (case-sensitive as stored in DB)
            if (role == null || !(role.equals("Producer") || role.equals("Subscriber"))) {
                role = "Subscriber"; // fallback to a safe default
                user.setRole(role);
                userRepository.save(user); // persist the fix once
            }

            // Use a mutable map (tolerates nulls and is friendlier than Map.of)
            Map<String, Object> body = new java.util.LinkedHashMap<>();
            body.put("username", user.getUsername());
            body.put("role", role);
            body.put("expiresAt", exp.toString());
            return ResponseEntity.ok(body);
        })
        .orElseGet(() ->
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid token"))
        );
}
}
