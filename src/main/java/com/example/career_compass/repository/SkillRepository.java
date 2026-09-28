package com.example.career_compass.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.career_compass.entity.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findBySkillNameIgnoreCase(String skillName);
    boolean existsBySkillNameIgnoreCase(String skillName);
}
