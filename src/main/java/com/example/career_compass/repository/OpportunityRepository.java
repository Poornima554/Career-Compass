package com.example.career_compass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.career_compass.entity.Opportunity;
import com.example.career_compass.entity.Sector;

public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {
    List<Opportunity> findBySector(Sector sector);
}
