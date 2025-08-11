package com.example.twitterbackend.controller;

import com.example.twitterbackend.model.UserRole;
import com.example.twitterbackend.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userroles")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/assign")
    public UserRole assignRole(@RequestParam Integer userId, @RequestParam Integer roleId) {
        return userRoleService.assignRole(userId, roleId);
    }

    @GetMapping("/role/{roleId}")
    public List<UserRole> getUsers(@PathVariable Integer roleId) {
        return userRoleService.getUsersByRoleId(roleId);
    }

    @GetMapping("/user/{userId}")
    public List<UserRole> getRoles(@PathVariable Integer userId) {
        return userRoleService.getRolesByUserId(userId);
    }

    
}
