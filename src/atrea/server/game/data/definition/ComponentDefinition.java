package atrea.server.game.data.definition;

import atrea.server.game.entities.components.EComponentType;
import lombok.Getter;

public class ComponentDefinition {

    private @Getter EComponentType type;
    private @Getter String[] values;

    public ComponentDefinition(EComponentType type, String[] values) {
        this.type = type;
        this.values = values;
    }
}