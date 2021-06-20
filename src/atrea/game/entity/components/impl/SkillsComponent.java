package atrea.game.entity.components.impl;

import atrea.game.content.skills.ESkill;
import atrea.game.content.skills.Skill;
import atrea.game.entity.Entity;
import atrea.game.entity.ExperienceAward;
import atrea.game.entity.SkillConstants;
import atrea.game.entity.components.EntityComponent;

import java.util.List;

public class SkillsComponent extends EntityComponent {

    private Skill[] skills;

    public SkillsComponent(Entity parent) {
        super(parent);
    }

    public void addExperience(ExperienceAward experience) {

    }

    public Skill getSkill(ESkill skill) {
        return skills[skill.getIndex()];
    }

    public boolean hasSkill(Skill skill) {
        return false;
    }

    public boolean hasSkill(ESkill skill) {
        /*for (Skill minor : minorSkills) {
            if (minor.)
        }*/
        return false;
    }
    }