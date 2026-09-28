package com.example.career_compass.dto.request;

import java.time.LocalDate;

import com.example.career_compass.entity.Sector;

public class OpportunityRequest {
    private String title;
    private String companyName;
    private String location;
    private Sector sector;
    private Double minimumCgpa;
    private LocalDate deadline;
    private String description;
    private String requiredSkills;
    private String tags;

    public OpportunityRequest() {}
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
}
