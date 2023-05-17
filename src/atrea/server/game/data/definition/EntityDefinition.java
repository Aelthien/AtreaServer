package atrea.server.game.data.definition;

import atrea.server.engine.world.EntitySize;
import atrea.server.game.entities.EInteractableType;
import lombok.Getter;

public class EntityDefinition {

    private @Getter int id;
    private @Getter String name;
    private @Getter String description;
    private @Getter
    EInteractableType entityType;
    private @Getter EntitySize size;
    private @Getter ComponentDefinition[] components;
}