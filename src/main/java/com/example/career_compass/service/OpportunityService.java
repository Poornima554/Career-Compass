package com.example.career_compass.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.career_compass.dto.request.OpportunityRequest;
import com.example.career_compass.dto.response.OpportunityResponse;
import com.example.career_compass.entity.Opportunity;
import com.example.career_compass.repository.OpportunityRepository;

@Service
public class OpportunityService {

    private final OpportunityRepository opportunityRepository;

    public OpportunityService(OpportunityRepository opportunityRepository) {
        this.opportunityRepository = opportunityRepository;
    }

    public OpportunityResponse create(OpportunityRequest request) {
        Opportunity opportunity = new Opportunity();
        copy(request, opportunity);
        return toResponse(opportunityRepository.save(opportunity));
    }

    public List<OpportunityResponse> findAll() {
        return opportunityRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Opportunity getEntity(Long id) {
        return opportunityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Opportunity not found"));
    }

    public OpportunityResponse get(Long id) {
        return toResponse(getEntity(id));
    }

    public void delete(Long id) {
        opportunityRepository.deleteById(id);
    }

    private void copy(OpportunityRequest request, Opportunity opportunity) {
        opportunity.setTitle(request.getTitle());
        opportunity.setCompanyName(request.getCompanyName());
        opportunity.setLocation(request.getLocation());
        opportunity.setSector(request.getSector());
        opportunity.setMinimumCgpa(request.getMinimumCgpa());
        opportunity.setDeadline(request.getDeadline());
        opportunity.setDescription(request.getDescription());
        opportunity.setRequiredSkills(request.getRequiredSkills());
        opportunity.setTags(request.getTags());
    }

    public OpportunityResponse toResponse(Opportunity opportunity) {
        return new OpportunityResponse(
                opportunity.getOpportunityId(),
                opportunity.getTitle(),
                opportunity.getCompanyName(),
                opportunity.getLocation(),
                opportunity.getSector(),
                opportunity.getMinimumCgpa(),
                opportunity.getDeadline(),
                opportunity.getDescription(),
                opportunity.getRequiredSkills(),
                opportunity.getTags());
    }
}
