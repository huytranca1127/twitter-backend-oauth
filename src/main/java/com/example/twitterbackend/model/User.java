package com.example.twitterbackend.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import java.time.Instant;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100)
    private String email;

    @Column(name = "token", length = 255) 
    private String token;

    @Column(name = "token_expiry")
    private Instant tokenExpiry;

    @Transient // not saved in DB, just used for incoming JSON
    private String role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "userrole",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    // --- Getters and Setters ---

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getTokenExpiry() {
    return tokenExpiry;
    }

    public void setTokenExpiry(Instant tokenExpiry) {
    this.tokenExpiry = tokenExpiry;
    }
}
