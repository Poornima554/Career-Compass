package com.example.career_compass.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.career_compass.dto.request.StatusRequest;
import com.example.career_compass.dto.response.ApplicationResponse;
import com.example.career_compass.service.ApplicationService;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/{opportunityId}")
    public ResponseEntity<ApplicationResponse> apply(Authentication authentication,
                                                      @PathVariable Long opportunityId) {
        return ResponseEntity.ok(applicationService.apply(authentication.getName(), opportunityId));
    }

    @GetMapping("/me")
    public ResponseEntity<List<ApplicationResponse>> myApplications(Authentication authentication) {
        return ResponseEntity.ok(applicationService.getMyApplications(authentication.getName()));
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> allApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @PutMapping("/{applicationId}/status")
    public ResponseEntity<ApplicationResponse> updateStatus(@PathVariable Long applicationId,
                                                             @RequestBody StatusRequest request) {
        return ResponseEntity.ok(applicationService.updateStatus(applicationId, request.getStatus()));
    }
}
