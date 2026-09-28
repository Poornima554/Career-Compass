package com.example.career_compass.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.career_compass.dto.request.ProfileRequest;
import com.example.career_compass.dto.response.ProfileResponse;
import com.example.career_compass.dto.response.SkillResponse;
import com.example.career_compass.entity.Skill;
import com.example.career_compass.entity.StudentProfile;
import com.example.career_compass.entity.StudentSkill;
import com.example.career_compass.entity.User;
import com.example.career_compass.repository.SkillRepository;
import com.example.career_compass.repository.StudentProfileRepository;
import com.example.career_compass.repository.StudentSkillRepository;
import com.example.career_compass.repository.UserRepository;

@Service
public class StudentService {

    private final UserRepository userRepository;
    private final StudentProfileRepository profileRepository;
    private final SkillRepository skillRepository;
    private final StudentSkillRepository studentSkillRepository;

    public StudentService(UserRepository userRepository,
                          StudentProfileRepository profileRepository,
                          SkillRepository skillRepository,
                          StudentSkillRepository studentSkillRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.skillRepository = skillRepository;
        this.studentSkillRepository = studentSkillRepository;
    }

    public StudentProfile getOrCreateProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return profileRepository.findByUser(user).orElseGet(() -> {
            StudentProfile profile = new StudentProfile();
            profile.setUser(user);
            return profileRepository.save(profile);
        });
    }

    public ProfileResponse getProfile(String email) {
        return toResponse(getOrCreateProfile(email));
    }

    public ProfileResponse updateProfile(String email, ProfileRequest request) {
        StudentProfile profile = getOrCreateProfile(email);
        profile.setCollegeName(request.getCollegeName());
        profile.setDegree(request.getDegree());
        profile.setGraduationYear(request.getGraduationYear());
        profile.setCgpa(request.getCgpa());
        profile.setSector(request.getSector());
        profile.setInterests(request.getInterests());
        profile.setGithubUrl(request.getGithubUrl());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        return toResponse(profileRepository.save(profile));
    }

    public List<SkillResponse> getSkills(String email) {
        StudentProfile profile = getOrCreateProfile(email);
        return studentSkillRepository.findByStudentProfile(profile).stream()
                .map(StudentSkill::getSkill)
                .map(skill -> new SkillResponse(skill.getSkillId(), skill.getSkillName()))
                .collect(Collectors.toList());
    }

    public void addSkill(String email, Long skillId) {
        StudentProfile profile = getOrCreateProfile(email);
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new IllegalArgumentException("Skill not found"));
        if (studentSkillRepository.findByStudentProfileAndSkill(profile, skill).isEmpty()) {
            StudentSkill studentSkill = new StudentSkill();
            studentSkill.setStudentProfile(profile);
            studentSkill.setSkill(skill);
            studentSkillRepository.save(studentSkill);
        }
    }

    public void removeSkill(String email, Long skillId) {
        StudentProfile profile = getOrCreateProfile(email);
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new IllegalArgumentException("Skill not found"));
        studentSkillRepository.findByStudentProfileAndSkill(profile, skill)
                .ifPresent(studentSkillRepository::delete);
    }

    public StudentProfile getProfileEntity(String email) {
        return getOrCreateProfile(email);
    }

    private ProfileResponse toResponse(StudentProfile profile) {
        ProfileResponse response = new ProfileResponse();
        response.setProfileId(profile.getProfileId());
        response.setFullName(profile.getUser().getFullName());
        response.setEmail(profile.getUser().getEmail());
        response.setCollegeName(profile.getCollegeName());
        response.setDegree(profile.getDegree());
        response.setGraduationYear(profile.getGraduationYear());
        response.setCgpa(profile.getCgpa());
        response.setSector(profile.getSector());
        response.setInterests(profile.getInterests());
        response.setGithubUrl(profile.getGithubUrl());
        response.setLinkedinUrl(profile.getLinkedinUrl());
        return response;
    }
}
