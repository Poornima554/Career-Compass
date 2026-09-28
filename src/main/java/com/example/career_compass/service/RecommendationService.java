package com.example.career_compass.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.career_compass.dto.response.OpportunityResponse;
import com.example.career_compass.dto.response.RecommendationResponse;
import com.example.career_compass.entity.Opportunity;
import com.example.career_compass.entity.Skill;
import com.example.career_compass.entity.StudentProfile;
import com.example.career_compass.entity.StudentSkill;
import com.example.career_compass.repository.OpportunityRepository;
import com.example.career_compass.repository.StudentSkillRepository;

@Service
public class RecommendationService {

    private final OpportunityRepository opportunityRepository;
    private final StudentService studentService;
    private final StudentSkillRepository studentSkillRepository;
    private final OpportunityService opportunityService;

    public RecommendationService(OpportunityRepository opportunityRepository,
                                  StudentService studentService,
                                  StudentSkillRepository studentSkillRepository,
                                  OpportunityService opportunityService) {
        this.opportunityRepository = opportunityRepository;
        this.studentService = studentService;
        this.studentSkillRepository = studentSkillRepository;
        this.opportunityService = opportunityService;
    }

    public List<RecommendationResponse> recommend(String email) {
        StudentProfile profile = studentService.getProfileEntity(email);
        Set<String> studentSkills = studentSkillRepository.findByStudentProfile(profile).stream()
                .map(StudentSkill::getSkill)
                .map(Skill::getSkillName)
                .map(this::normalize)
                .collect(Collectors.toSet());

        String interests = normalize(profile.getInterests());
        List<RecommendationResponse> results = new ArrayList<>();

        for (Opportunity opportunity : opportunityRepository.findAll()) {
            if (opportunity.getDeadline() != null && opportunity.getDeadline().isBefore(java.time.LocalDate.now())) {
                continue;
            }

            if (opportunity.getMinimumCgpa() != null &&
                    (profile.getCgpa() == null || profile.getCgpa() < opportunity.getMinimumCgpa())) {
                continue;
            }

            List<String> required = splitValues(opportunity.getRequiredSkills());
            List<String> matched = required.stream()
                    .filter(skill -> studentSkills.contains(normalize(skill)))
                    .collect(Collectors.toList());
            List<String> missing = required.stream()
                    .filter(skill -> !studentSkills.contains(normalize(skill)))
                    .collect(Collectors.toList());

            double sectorScore = profile.getSector() != null && profile.getSector() == opportunity.getSector() ? 45 : 5;
            double skillScore = required.isEmpty() ? 20 : (matched.size() * 25.0 / required.size());
            double interestScore = tagsMatch(interests, opportunity.getTags()) ? 20 : 0;
            double cgpaScore = 10;
            double score = Math.min(100, sectorScore + skillScore + interestScore + cgpaScore);

            RecommendationResponse recommendation = new RecommendationResponse();
            OpportunityResponse response = opportunityService.toResponse(opportunity);
            response.setMatchScore(score);
            response.setMatchNote(buildNote(profile, opportunity, matched, missing, score));
            recommendation.setOpportunity(response);
            recommendation.setMatchScore(score);
            recommendation.setMatchedSkills(matched);
            recommendation.setMissingSkills(missing);
            recommendation.setReason(buildReason(profile, opportunity, matched, score));
            results.add(recommendation);
        }

        results.sort(Comparator.comparingDouble(RecommendationResponse::getMatchScore).reversed());
        return results.stream().limit(8).collect(Collectors.toList());
    }

    private String buildReason(StudentProfile profile, Opportunity opportunity, List<String> matched, double score) {
        if (profile.getSector() == opportunity.getSector()) {
            return "Your sector matches this opportunity. " + matched.size() + " required skill(s) match your profile.";
        }
        return "This is a cross-sector suggestion based on your interests and skills.";
    }

    private String buildNote(StudentProfile profile, Opportunity opportunity,
                             List<String> matched, List<String> missing, double score) {
        String prefix = Math.round(score) + "% match. ";
        if (profile.getSector() == opportunity.getSector()) {
            prefix += "Sector match. ";
        }
        if (!matched.isEmpty()) {
            prefix += "Matched: " + String.join(", ", matched) + ". ";
        }
        if (!missing.isEmpty()) {
            prefix += "Skill to improve: " + String.join(", ", missing) + ".";
        }
        return prefix.trim();
    }

    private boolean tagsMatch(String interests, String tags) {
        if (interests == null || interests.isBlank() || tags == null || tags.isBlank()) return false;
        for (String token : splitValues(interests)) {
            if (normalize(tags).contains(normalize(token))) return true;
        }
        return false;
    }

    private List<String> splitValues(String value) {
        if (value == null || value.isBlank()) return List.of();
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());
    }

    private String normalize(String value) {
        return value == null ? "" : value.toLowerCase(Locale.ROOT).trim();
    }
}
