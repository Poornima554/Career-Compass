package com.example.career_compass.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.career_compass.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    long countByRole(com.example.career_compass.entity.Role role);
}
