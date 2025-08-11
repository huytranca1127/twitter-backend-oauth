package com.example.twitterbackend.repository;

import com.example.twitterbackend.model.UserRole;
import com.example.twitterbackend.model.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByUser_UserId(Integer userId);
    List<UserRole> findByRole_RoleId(Integer roleId);
}
