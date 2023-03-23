package atrea.server.game.entities.ecs.command;

import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.Entity;
import atrea.server.game.entities.ecs.systems.ComponentSystem;

import java.util.HashMap;
import java.util.Map;

public class ScriptSystem extends ComponentSystem<ScriptComponent> {

    public ScriptSystem() {
        components = new ScriptComponent[5000];
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        components[entity.getEntityId()] = new ScriptComponent(entity);
    }

    @Override public void update() {
        for (ScriptComponent component : components) {

        }
    }
}