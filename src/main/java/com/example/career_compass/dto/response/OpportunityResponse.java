package com.example.career_compass.dto.response;

import java.time.LocalDate;
import com.example.career_compass.entity.Sector;

public class OpportunityResponse {
    private Long opportunityId;
    private String title;
    private String companyName;
    private String location;
    private Sector sector;
    private Double minimumCgpa;
    private LocalDate deadline;
    private String description;
    private String requiredSkills;
    private String tags;
    private Double matchScore;
    private String matchNote;

    public OpportunityResponse() {}
    public OpportunityResponse(Long opportunityId, String title, String companyName, String location,
            Sector sector, Double minimumCgpa, LocalDate deadline, String description,
            String requiredSkills, String tags) {
        this.opportunityId = opportunityId; this.title = title; this.companyName = companyName;
        this.location = location; this.sector = sector; this.minimumCgpa = minimumCgpa;
        this.deadline = deadline; this.description = description; this.requiredSkills = requiredSkills; this.tags = tags;
    }
    public Long getOpportunityId() { return opportunityId; }
    public void setOpportunityId(Long opportunityId) { this.opportunityId = opportunityId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Sector getSector() { return sector; }
    public void setSector(Sector sector) { this.sector = sector; }
    public Double getMinimumCgpa() { return minimumCgpa; }
    public void setMinimumCgpa(Double minimumCgpa) { this.minimumCgpa = minimumCgpa; }
    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(String requiredSkills) { this.requiredSkills = requiredSkills; }
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    public Double getMatchScore() { return matchScore; }
    public void setMatchScore(Double matchScore) { this.matchScore = matchScore; }
    public String getMatchNote() { return matchNote; }
    public void setMatchNote(String matchNote) { this.matchNote = matchNote; }
}
