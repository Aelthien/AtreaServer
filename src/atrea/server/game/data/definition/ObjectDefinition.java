package atrea.game.data.definition;

import atrea.game.content.items.EItemType;
import atrea.game.entity.EntityStateInteractions;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ObjectDefinition {

    private static List<ObjectDefinition> objectDefinitions = new ArrayList<>();

    private @Getter int id;
    private @Getter String name;
    private @Getter EItemType type;
    private @Getter EntityStateInteractions[] interactions;
    private @Getter ComponentDefinition[] componentDefinitions;

    public static ObjectDefinition getDefinition(int id) {
        return objectDefinitions.get(id);
    }
}