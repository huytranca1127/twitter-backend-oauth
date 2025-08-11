package com.example.twitterbackend.init;

import com.example.twitterbackend.model.Role;
import com.example.twitterbackend.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        createRoleIfNotExists("Producer");
        createRoleIfNotExists("Subscriber");
    }

    private void createRoleIfNotExists(String roleName) {
        roleRepository.findByRoleName(roleName)
            .orElseGet(() -> {
                Role role = new Role();
                role.setRoleName(roleName);
                return roleRepository.save(role);
            });
    }
}
