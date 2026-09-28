package com.example.career_compass.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.career_compass.dto.request.OpportunityRequest;
import com.example.career_compass.dto.response.OpportunityResponse;
import com.example.career_compass.service.OpportunityService;

@RestController
@RequestMapping("/api/opportunities")
public class OpportunityController {

    private final OpportunityService opportunityService;

    public OpportunityController(OpportunityService opportunityService) {
        this.opportunityService = opportunityService;
    }

    @GetMapping
    public ResponseEntity<List<OpportunityResponse>> list() {
        return ResponseEntity.ok(opportunityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpportunityResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(opportunityService.get(id));
    }

    @PostMapping
    public ResponseEntity<OpportunityResponse> create(@RequestBody OpportunityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(opportunityService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        opportunityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
