package atrea.server.engine.accounts;

import atrea.server.game.content.skills.Skill;
import atrea.server.game.entities.ecs.skill.SkillsComponent;
import lombok.Getter;

import java.util.List;

public class CharacterSkillsData {
    private @Getter List<Skill> skills;

    public CharacterSkillsData(SkillsComponent skillComponent) {
        this.skills = List.of(skillComponent.getSkills());
    }

    public CharacterSkillsData(List<Skill> skills) {
        this.skills = skills;
    }
}