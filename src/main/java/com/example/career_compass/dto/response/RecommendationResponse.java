package com.example.career_compass.dto.response;

import java.util.List;

public class RecommendationResponse {
    private OpportunityResponse opportunity;
    private double matchScore;
    private List<String> matchedSkills;
    private List<String> missingSkills;
    private String reason;

    public RecommendationResponse() {}
    public OpportunityResponse getOpportunity() { return opportunity; }
    public void setOpportunity(OpportunityResponse opportunity) { this.opportunity = opportunity; }
    public double getMatchScore() { return matchScore; }
    public void setMatchScore(double matchScore) { this.matchScore = matchScore; }
    public List<String> getMatchedSkills() { return matchedSkills; }
    public void setMatchedSkills(List<String> matchedSkills) { this.matchedSkills = matchedSkills; }
    public List<String> getMissingSkills() { return missingSkills; }
    public void setMissingSkills(List<String> missingSkills) { this.missingSkills = missingSkills; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
