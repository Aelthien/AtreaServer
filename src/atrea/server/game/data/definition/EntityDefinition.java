package atrea.server.game.data.definition;

import atrea.server.engine.world.EntitySize;
import atrea.server.game.content.items.EItemType;
import atrea.server.game.entities.EEntityType;
import atrea.server.game.entities.EntityStateInteractions;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class EntityDefinition {

    private @Getter int id;
    private @Getter String name;
    private @Getter String description;
    private @Getter EEntityType entityType;
    private @Getter EntitySize size;
    private @Getter ComponentDefinition[] components;
}