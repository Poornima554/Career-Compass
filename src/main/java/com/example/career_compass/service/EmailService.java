package com.example.career_compass.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.career_compass.entity.Application;
import com.example.career_compass.entity.ApplicationStatus;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendApplicationStatusEmail(
            Application application,
            ApplicationStatus oldStatus) {

        String studentEmail =
                application.getStudentProfile()
                           .getUser()
                           .getEmail();

        String studentName =
                application.getStudentProfile()
                           .getUser()
                           .getFullName();

        String opportunityTitle =
                application.getOpportunity()
                           .getTitle();

        String companyName =
                application.getOpportunity()
                           .getCompanyName();

        ApplicationStatus newStatus =
                application.getStatus();

        String subject =
                "CareerCompass - Application Status Update";

        String message =
                "Hello " + studentName + ",\n\n"
                + "There has been an update to your application.\n\n"
                + "Opportunity: " + opportunityTitle + "\n"
                + "Company: " + companyName + "\n\n"
                + "Previous Status: " + formatStatus(oldStatus) + "\n"
                + "Current Status: " + formatStatus(newStatus) + "\n\n"
                + getStatusMessage(newStatus)
                + "\n\n"
                + "You can log in to CareerCompass to view your "
                + "application details.\n\n"
                + "Regards,\n"
                + "CareerCompass Team";

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(studentEmail);
        mail.setSubject(subject);
        mail.setText(message);

        mailSender.send(mail);
    }

    private String formatStatus(ApplicationStatus status) {

        if (status == null) {
            return "N/A";
        }

        return status.name()
                .replace("_", " ")
                .toLowerCase();
    }

    private String getStatusMessage(ApplicationStatus status) {

        switch (status) {

            case APPLIED:
                return "Your application has been successfully submitted.";

            case UNDER_REVIEW:
                return "Your application is currently under review.";

            case INTERVIEW_SCHEDULED:
                return "An interview has been scheduled. Please check your application details for further information.";

            case SELECTED:
                return "Congratulations! Your application has been selected.";

            case REJECTED:
                return "Unfortunately, your application was not selected at this time.";

            default:
                return "Your application status has been updated.";
        }
    }
}