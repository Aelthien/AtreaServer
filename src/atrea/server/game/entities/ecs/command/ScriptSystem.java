package atrea.server.game.entities.ecs.command;

import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.systems.ComponentSystem;

public class ScriptSystem extends ComponentSystem<ScriptComponent> {

    public ScriptSystem() {
        componentsArray = new ScriptComponent[5000];
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        componentsArray[entity.getEntityId()] = new ScriptComponent(entity);
    }

    @Override public void update() {
        for (ScriptComponent component : componentsArray) {

        }
    }
}