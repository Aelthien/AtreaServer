package atrea.game.data.definition;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class NPCDefinition {

    private static List<NPCDefinition> definitions = new ArrayList<>();

    private @Getter int id;
    private @Getter String name;
    private @Getter ComponentDefinition[] componentDefinitions;

    public static NPCDefinition getDefinition(int id) {
        return definitions.get(id);
    }
}