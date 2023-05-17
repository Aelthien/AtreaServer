package atrea.server.game.data.definition;

import atrea.server.game.content.items.RequiredItem;
import atrea.server.game.content.items.RequiredSkill;
import atrea.server.game.content.skills.ExperienceAward;
import atrea.server.game.content.spells.EAbilityType;
import atrea.server.game.entities.ecs.summon.ESummonType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class SummonDefinition {

    private @Getter int id;
    private @Getter String name;
    private @Getter String description;

    private @Getter int entityDefinitionId;

    private @Getter ESummonType summonType;

    private @Getter RequiredItem[] requiredItems;
    private @Getter RequiredSkill[] requiredSkills;

    private @Getter ExperienceAward[] experienceAwards;
}