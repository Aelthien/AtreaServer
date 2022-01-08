package atrea.game.data.definition;

import lombok.Getter;

public class ComponentDefinition {

    private @Getter String type;
    private @Getter String[] values;

    public ComponentDefinition(String type, String[] values) {
        this.type = type;
        this.values = values;
    }
}