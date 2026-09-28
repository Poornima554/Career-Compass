package com.example.career_compass.dto.response;

public class SkillResponse {
    private Long skillId;
    private String skillName;
    public SkillResponse() {}
    public SkillResponse(Long skillId, String skillName) { this.skillId = skillId; this.skillName = skillName; }
    public Long getSkillId() { return skillId; }
    public void setSkillId(Long skillId) { this.skillId = skillId; }
    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
}
