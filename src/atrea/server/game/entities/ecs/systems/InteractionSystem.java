package atrea.server.game.entities.ecs.systems;

import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.Entity;
import atrea.server.game.entities.ecs.InteractionComponent;

import java.util.HashMap;
import java.util.Map;

public class InteractionSystem extends ComponentSystem<InteractionComponent> {

    private Map<Integer, Class> scriptMap = new HashMap<>();

    public InteractionSystem() {
        components = new InteractionComponent[5000];
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        components[entity.getEntityId()] = new InteractionComponent(entity, definition.getValues());
    }

    @Override public void update() {
    }
}
