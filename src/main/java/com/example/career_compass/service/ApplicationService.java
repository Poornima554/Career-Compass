package com.example.career_compass.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.career_compass.dto.response.ApplicationResponse;
import com.example.career_compass.entity.Application;
import com.example.career_compass.entity.ApplicationStatus;
import com.example.career_compass.entity.Opportunity;
import com.example.career_compass.entity.StudentProfile;
import com.example.career_compass.repository.ApplicationRepository;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentService studentService;
    private final OpportunityService opportunityService;
    private EmailService emailService;

    public ApplicationService(ApplicationRepository applicationRepository,
                              StudentService studentService,
                              OpportunityService opportunityService, EmailService emailService) {
        this.applicationRepository = applicationRepository;
        this.studentService = studentService;
        this.opportunityService = opportunityService;
        this.emailService = emailService;
    }

    public ApplicationResponse apply(String email, Long opportunityId) {
        StudentProfile profile = studentService.getProfileEntity(email);
        Opportunity opportunity = opportunityService.getEntity(opportunityId);

        if (opportunity.getDeadline() != null && opportunity.getDeadline().isBefore(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("Application deadline has passed");
        }

        if (applicationRepository.findByStudentProfileAndOpportunity(profile, opportunity).isPresent()) {
            throw new IllegalArgumentException("You have already applied for this opportunity");
        }

        Application application = new Application();
        application.setStudentProfile(profile);
        application.setOpportunity(opportunity);
        application.setStatus(ApplicationStatus.APPLIED);
        return toResponse(applicationRepository.save(application));
    }

    public List<ApplicationResponse> getMyApplications(String email) {
        StudentProfile profile = studentService.getProfileEntity(email);
        return applicationRepository.findByStudentProfileOrderByAppliedAtDesc(profile)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<ApplicationResponse> getAllApplications() {
        return applicationRepository.findAll().stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    public ApplicationResponse updateStatus(
            Long applicationId,
            ApplicationStatus status) {

        Application application =
                applicationRepository.findById(applicationId)
                    .orElseThrow(() ->
                        new IllegalArgumentException(
                            "Application not found"));

        ApplicationStatus oldStatus =
                application.getStatus();

        // No need to send an email if the status hasn't changed
        if (oldStatus == status) {
            return toResponse(application);
        }

        application.setStatus(status);

        Application savedApplication =
                applicationRepository.save(application);

        try {

            emailService.sendApplicationStatusEmail(
                    savedApplication,
                    oldStatus
            );

        } catch (Exception e) {

            // Email failure should not undo the status update
            System.err.println(
                    "Failed to send status update email: "
                    + e.getMessage()
            );
        }

        return toResponse(savedApplication);
    }

    public long count() {
        return applicationRepository.count();
    }

    private ApplicationResponse toResponse(Application application) {
        ApplicationResponse response = new ApplicationResponse();
        response.setApplicationId(application.getApplicationId());
        response.setOpportunityId(application.getOpportunity().getOpportunityId());
        response.setOpportunityTitle(application.getOpportunity().getTitle());
        response.setCompanyName(application.getOpportunity().getCompanyName());
        response.setStudentName(application.getStudentProfile().getUser().getFullName());
        response.setStatus(application.getStatus());
        response.setAppliedAt(application.getAppliedAt());
        return response;
    }
}
