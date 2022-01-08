package atrea.game.data.definition;

import atrea.game.content.items.RequiredItem;
import atrea.game.content.items.RequiredSkill;
import atrea.game.content.spells.EAbilityType;
import atrea.game.content.skills.ExperienceAward;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class AbilityDefinition {

    private static List<AbilityDefinition> definitions = new ArrayList<>();

    private @Getter @Setter int id;
    private @Getter @Setter String name;

    private @Getter @Setter RequiredItem[] requiredItems;
    private @Getter @Setter RequiredSkill[] requiredSkills;
    private @Getter @Setter
    EAbilityType type;

    private @Getter @Setter ExperienceAward[] experienceAward;

    public static AbilityDefinition getDefinition(int id) {
        return definitions.get(id);
    }
}