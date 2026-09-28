package com.example.career_compass.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.career_compass.entity.Skill;
import com.example.career_compass.entity.StudentProfile;
import com.example.career_compass.entity.StudentSkill;

public interface StudentSkillRepository extends JpaRepository<StudentSkill, Long> {
    List<StudentSkill> findByStudentProfile(StudentProfile studentProfile);
    Optional<StudentSkill> findByStudentProfileAndSkill(StudentProfile studentProfile, Skill skill);
}
