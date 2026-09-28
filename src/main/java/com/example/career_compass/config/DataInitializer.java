package com.example.career_compass.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.career_compass.entity.Opportunity;
import com.example.career_compass.entity.Role;
import com.example.career_compass.entity.Sector;
import com.example.career_compass.entity.Skill;
import com.example.career_compass.entity.User;
import com.example.career_compass.repository.OpportunityRepository;
import com.example.career_compass.repository.SkillRepository;
import com.example.career_compass.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedData(UserRepository userRepository,
                               SkillRepository skillRepository,
                               OpportunityRepository opportunityRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByEmail("admin@careercompass.com")) {
                User admin = new User();
                admin.setFullName("CareerCompass Admin");
                admin.setEmail("admin@careercompass.com");
                admin.setPassword(passwordEncoder.encode("Admin@123"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
            }

            if (skillRepository.count() == 0) {
                for (String name : List.of(
                        "Java", "Spring Boot", "SQL", "Python", "AutoCAD", "SolidWorks",
                        "Figma", "Graphic Design", "Communication", "Sales", "Digital Marketing",
                        "Excel", "Business Analysis", "Content Writing", "Video Editing")) {
                    Skill skill = new Skill();
                    skill.setSkillName(name);
                    skillRepository.save(skill);
                }
            }

            if (opportunityRepository.count() == 0) {
                add(opportunityRepository, "Java Backend Intern", "CodeSpring Labs", Sector.TECHNOLOGY,
                        "Java, Spring Boot, SQL", "backend, software, api", 7.0,
                        "Build REST APIs and backend services.", "Bengaluru / Hybrid");
                add(opportunityRepository, "Data Analyst Intern", "InsightWorks", Sector.TECHNOLOGY,
                        "Python, SQL, Excel", "analytics, data", 6.5,
                        "Work with business datasets and dashboards.", "Remote");
                add(opportunityRepository, "Mechanical Design Intern", "MechNova", Sector.CORE_ENGINEERING,
                        "AutoCAD, SolidWorks", "design, manufacturing", 6.0,
                        "Support CAD modelling and manufacturing documentation.", "Pune");
                add(opportunityRepository, "Site Engineering Intern", "BuildGrid", Sector.CORE_ENGINEERING,
                        "AutoCAD, Excel", "civil, site, construction", 6.0,
                        "Assist site teams with drawings and project tracking.", "Bengaluru");
                add(opportunityRepository, "UI/UX Design Intern", "PixelCraft", Sector.DESIGN_CREATIVE,
                        "Figma, Graphic Design", "design, ux, creative", 6.0,
                        "Create wireframes and simple design systems.", "Remote");
                add(opportunityRepository, "Content Creator Intern", "StoryFrame Media", Sector.DESIGN_CREATIVE,
                        "Content Writing, Video Editing", "creator, content, social media", 6.0,
                        "Plan and produce short-form digital content.", "Hybrid");
                add(opportunityRepository, "Sales Intern", "MarketReach", Sector.SALES_MARKETING,
                        "Communication, Sales", "sales, business development", 5.5,
                        "Support lead generation and client outreach.", "Bengaluru");
                add(opportunityRepository, "Digital Marketing Intern", "BrandPilot", Sector.SALES_MARKETING,
                        "Digital Marketing, Communication", "marketing, social media, growth", 6.0,
                        "Support campaigns, content planning and growth reporting.", "Remote");
                add(opportunityRepository, "Business Operations Intern", "OpsBridge", Sector.BUSINESS_OPERATIONS,
                        "Excel, Communication", "operations, process, coordination", 6.0,
                        "Help teams improve daily processes and reporting.", "Chennai");
                add(opportunityRepository, "Business Analyst Intern", "ProcessIQ", Sector.BUSINESS_OPERATIONS,
                        "Excel, Business Analysis, SQL", "business analysis, operations", 7.0,
                        "Translate business needs into simple reports and requirements.", "Hyderabad");
            }
        };
    }

    private void add(OpportunityRepository repo, String title, String company, Sector sector,
                     String skills, String tags, double cgpa, String description, String location) {
        Opportunity opportunity = new Opportunity();
        opportunity.setTitle(title);
        opportunity.setCompanyName(company);
        opportunity.setSector(sector);
        opportunity.setRequiredSkills(skills);
        opportunity.setTags(tags);
        opportunity.setMinimumCgpa(cgpa);
        opportunity.setDescription(description);
        opportunity.setLocation(location);
        opportunity.setDeadline(LocalDate.now().plusDays(60));
        repo.save(opportunity);
    }
}
