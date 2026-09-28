package com.example.career_compass.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.career_compass.dto.request.ProfileRequest;
import com.example.career_compass.dto.response.ProfileResponse;
import com.example.career_compass.dto.response.SkillResponse;
import com.example.career_compass.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getProfile(Authentication authentication) {
        return ResponseEntity.ok(studentService.getProfile(authentication.getName()));
    }

    @PutMapping("/me")
    public ResponseEntity<ProfileResponse> updateProfile(Authentication authentication,
                                                          @RequestBody ProfileRequest request) {
        return ResponseEntity.ok(studentService.updateProfile(authentication.getName(), request));
    }

    @GetMapping("/me/skills")
    public ResponseEntity<List<SkillResponse>> getSkills(Authentication authentication) {
        return ResponseEntity.ok(studentService.getSkills(authentication.getName()));
    }

    @PostMapping("/me/skills/{skillId}")
    public ResponseEntity<Void> addSkill(Authentication authentication, @PathVariable Long skillId) {
        studentService.addSkill(authentication.getName(), skillId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me/skills/{skillId}")
    public ResponseEntity<Void> removeSkill(Authentication authentication, @PathVariable Long skillId) {
        studentService.removeSkill(authentication.getName(), skillId);
        return ResponseEntity.noContent().build();
    }
}
