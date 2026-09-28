package com.example.career_compass.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.career_compass.entity.Role;
import com.example.career_compass.repository.SkillRepository;
import com.example.career_compass.service.ApplicationService;
import com.example.career_compass.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final SkillRepository skillRepository;
    private final ApplicationService applicationService;
    private final com.example.career_compass.repository.OpportunityRepository opportunityRepository;

    public AdminController(UserService userService,
                           SkillRepository skillRepository,
                           ApplicationService applicationService,
                           com.example.career_compass.repository.OpportunityRepository opportunityRepository) {
        this.userService = userService;
        this.skillRepository = skillRepository;
        this.applicationService = applicationService;
        this.opportunityRepository = opportunityRepository;
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> stats() {
        return ResponseEntity.ok(Map.of(
                "students", userService.findAll().stream().filter(u -> u.getRole() == Role.STUDENT).count(),
                "opportunities", opportunityRepository.count(),
                "applications", applicationService.count(),
                "skills", skillRepository.count()));
    }
}
