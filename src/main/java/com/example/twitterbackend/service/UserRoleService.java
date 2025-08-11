package com.example.twitterbackend.service;

import com.example.twitterbackend.model.*;
import com.example.twitterbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public UserRole assignRole(Integer userId, Integer roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        UserRole userRole = new UserRole(user, role);
        return userRoleRepository.save(userRole);
    }

    public List<UserRole> getRolesByUserId(Integer userId) {
        return userRoleRepository.findByUser_UserId(userId);
    }

    public List<UserRole> getUsersByRoleId(Integer roleId) {
        return userRoleRepository.findByRole_RoleId(roleId);
    }
}
