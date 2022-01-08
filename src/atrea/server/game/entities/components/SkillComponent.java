package atrea.server.game.entity.components;

import atrea.server.game.content.skills.ESkill;
import atrea.server.game.content.skills.Skill;
import atrea.server.game.entity.Entity;

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

    public boolean hasSkill(ESkill skill, int level) {
        for (Skill checkedSkill : skills) {
            if (checkedSkill.getSkill() == skill) {
                if (checkedSkill.getCurrentLevel() == level)
                    return true;
            }
        }

        return false;
    }
}