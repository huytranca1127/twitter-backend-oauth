package com.example.twitterbackend.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.twitterbackend.model.User;
import com.example.twitterbackend.repository.UserRepository;

@RestController
public class OAuthSuccessController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/token")
    public ResponseEntity<String> generateToken(OAuth2AuthenticationToken authentication) {
    // GitHub username (login)
    String username = authentication.getPrincipal().getAttribute("login");

    User user = userRepository.findByUsername(username)
        .orElseGet(() -> {
            User u = new User();
            u.setUsername(username);
            u.setPassword("");              // no password used for GitHub auth
            // default to a valid role for this assignment
            u.setRole("Subscriber");
            return u;
        });

            // normalize existing role if it's null/invalid (e.g., "user")
    String role = user.getRole();
    if (!"Producer".equals(role) && !"Subscriber".equals(role)) {
        user.setRole("Subscriber");
    }

            // issue fresh token with 15-minute expiry
    String uuidToken = UUID.randomUUID().toString();
    Instant expiry = Instant.now().plus(Duration.ofMinutes(15));
    user.setToken(uuidToken);
    user.setTokenExpiry(expiry);

    userRepository.save(user);

    return ResponseEntity.ok("Your token is: " + uuidToken);
}

}
