package com.example.career_compass.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.career_compass.dto.response.SkillResponse;
import com.example.career_compass.entity.Skill;
import com.example.career_compass.repository.SkillRepository;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public SkillResponse create(String name) {
        String cleaned = name.trim();
        if (skillRepository.existsBySkillNameIgnoreCase(cleaned)) {
            throw new IllegalArgumentException("Skill already exists");
        }
        Skill skill = new Skill();
        skill.setSkillName(cleaned);
        skill = skillRepository.save(skill);
        return new SkillResponse(skill.getSkillId(), skill.getSkillName());
    }

    public List<SkillResponse> findAll() {
        return skillRepository.findAll().stream()
                .map(skill -> new SkillResponse(skill.getSkillId(), skill.getSkillName()))
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        skillRepository.deleteById(id);
    }
}
