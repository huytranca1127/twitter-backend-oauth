package com.example.twitterbackend.controller;

import com.example.twitterbackend.model.User;
import com.example.twitterbackend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class ProtectedController {

    private final UserRepository userRepository;

    public ProtectedController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/api/hello")
    public ResponseEntity<?> hello(@RequestHeader(name = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Missing or invalid Authorization header"));
        }

        // Trim and strip accidental < > copied from UI
        String token = authHeader.substring(7).trim().replaceAll("[<>]", "");

        Optional<User> optionalUser = userRepository.findByToken(token);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid or expired token"));
        }

        User user = optionalUser.get();
        Instant exp = user.getTokenExpiry();
        if (exp == null || Instant.now().isAfter(exp)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid or expired token"));
        }

        // Normalize role to avoid nulls and keep only allowed values
        String role = user.getRole();
        if (!"Producer".equals(role) && !"Subscriber".equals(role)) {
            role = "Subscriber";
            user.setRole(role);
            userRepository.save(user); // persist the fix once
        }

        // Build success body with a mutable map (tolerates nulls, but we normalized above)
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Hello, " + user.getUsername() + "!");
        body.put("role", role);
        body.put("expiresAt", exp.toString());

        return ResponseEntity.ok(body);
    }
}
