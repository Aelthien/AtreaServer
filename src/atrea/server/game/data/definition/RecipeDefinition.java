package atrea.game.data.definition;

import atrea.game.content.items.Item;
import atrea.game.content.items.RequiredItem;
import atrea.game.content.items.RequiredSkill;
import atrea.game.content.skills.ExperienceAward;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class RecipeDefinition {

    private static List<RecipeDefinition> definitions = new ArrayList<>();

    private @Getter @Setter int id;
    private @Getter @Setter String name;

    private @Getter @Setter RequiredItem primaryItem;
    private @Getter @Setter RequiredItem secondaryItem;
    private @Getter @Setter RequiredSkill[] requiredSkills;
    //private @Getter @Setter
    private @Getter @Setter ExperienceAward[] experienceAward;

    private @Getter @Setter Item producedItem;

    public static RecipeDefinition getDefinition(int id) {
        return definitions.get(id);
    }
}