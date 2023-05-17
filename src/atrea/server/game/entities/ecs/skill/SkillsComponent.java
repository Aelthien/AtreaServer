package atrea.server.game.entities.ecs.skill;

import atrea.server.game.content.items.RequiredSkill;
import atrea.server.game.content.skills.ESkill;
import atrea.server.game.content.skills.ExperienceAward;
import atrea.server.game.content.skills.Skill;
import atrea.server.game.entities.ecs.EComponentType;
import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.EntityComponent;
import lombok.Getter;

import static atrea.server.game.entities.ecs.EComponentType.*;

public class SkillsComponent extends EntityComponent {

    private final int MAX_SKILLS = 8;

    private @Getter Skill[] skills;

    @Override public EComponentType getComponentType() {
        return SKILLS;
    }

    public SkillsComponent(Entity parent) {
        super(parent);

        skills = new Skill[MAX_SKILLS];
    }

    @Override public void reset() {

    }

    public Skill getSkill(ESkill skill) {
        return skills[skill.ordinal()];
    }

    public void setMaxLevel(ESkill skill, int level) {
        skills[skill.ordinal()].setMaxLevel(level);
    }

    public void setAllMaxLevels(int[] levels) {
        int skillNumber = skills.length;

        for (int i = 0; i < skillNumber; i++) {
            skills[i].setMaxLevel(levels[i]);
        }
    }

    public void setCurrentLevel(ESkill skill, int level) {
        skills[skill.ordinal()].setCurrentLevel(level);
    }

    public void setAllCurrentLevels(int[] levels) {
        int skillNumber = skills.length;

        for (int i = 0; i < skillNumber; i++) {
            skills[i].setCurrentLevel(levels[i]);
        }
    }

    public boolean hasSkills(RequiredSkill[] requiredSkills) {
        for (RequiredSkill requiredSkill : requiredSkills) {
            if (!hasSkill(requiredSkill)) {
                return false;
            }
        }

        return true;
    }
    public boolean hasSkill(RequiredSkill requiredSkill) {
        return hasSkill(requiredSkill.getSkill(), requiredSkill.getLevel());
    }

    public boolean hasSkill(ESkill skillValue, int level) {
        Skill skill = skills[skillValue.ordinal()];

        return skill.getCurrentLevel() >= level;
    }

    public void setSkill(int index, Skill skill) {
        skills[index] = skill;

        skill.setNeedsUpdate(true);
    }

    public void setSkills(Skill[] skills) {
        for (int i = 0; i < skills.length; i++) {
            setSkill(i, skills[i]);
        }
    }

    public void grantExperience(ESkill skill, int experience) {
        getSkill(skill).addExperience(experience);
    }

    public void grantExperience(ExperienceAward experienceAwards) {
        grantExperience(experienceAwards.getSkill(), experienceAwards.getExperience());
    }
}