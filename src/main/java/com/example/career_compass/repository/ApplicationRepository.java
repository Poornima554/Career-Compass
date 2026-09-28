package com.example.career_compass.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.career_compass.entity.Application;
import com.example.career_compass.entity.ApplicationStatus;
import com.example.career_compass.entity.StudentProfile;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudentProfileOrderByAppliedAtDesc(StudentProfile studentProfile);
    Optional<Application> findByStudentProfileAndOpportunity(
            StudentProfile studentProfile,
            com.example.career_compass.entity.Opportunity opportunity);
    long countByStatus(ApplicationStatus status);
}
