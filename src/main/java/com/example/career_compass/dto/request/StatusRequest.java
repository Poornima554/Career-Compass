package com.example.career_compass.dto.request;

import com.example.career_compass.entity.ApplicationStatus;

public class StatusRequest {
    private ApplicationStatus status;
    public StatusRequest() {}
    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
}
