package atrea.game.entity.components.impl;

import atrea.game.content.skills.ESkill;
import atrea.game.content.skills.Skill;
import atrea.game.entity.Entity;
import atrea.game.entity.components.EntityComponent;

public class SkillComponent extends EntityComponent {

    private Skill[] skills;

    public SkillComponent(Entity parent) {
        super(parent);
    }

    public Skill getSkill(ESkill skill) {
        return skills[skill.ordinal()];
    }

    public Skill[] getSkills() {
        return skills;
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
}