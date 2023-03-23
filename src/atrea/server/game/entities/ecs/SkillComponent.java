package atrea.server.game.entities.ecs;

import atrea.server.game.content.skills.ESkill;
import atrea.server.game.content.skills.Skill;
import lombok.Getter;
import lombok.Setter;

import static atrea.server.game.entities.ecs.EComponentType.*;

public class SkillComponent extends EntityComponent {

    private final int MAX_SKILLS = 5;

    private @Getter @Setter Skill[] skills;

    @Override public EComponentType getComponentType() {
        return SKILL;
    }

    public SkillComponent(Entity parent) {
        super(parent);

        skills = new Skill[MAX_SKILLS];
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

    public boolean hasSkill(ESkill skill, int level) {
        for (Skill checkedSkill : skills) {
            if (checkedSkill.getSkill() == skill) {
                if (checkedSkill.getCurrentLevel() == level)
                    return true;
            }
        }

        return false;
    }

    public void setSkill(int index, Skill skill) {
        skill.setNeedsUpdate(true);

        skills[index] = skill;
    }
}