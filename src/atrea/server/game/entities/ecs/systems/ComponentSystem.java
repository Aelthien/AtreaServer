package atrea.server.game.entities.ecs.systems;

import atrea.server.game.entities.ecs.Entity;
import atrea.server.game.entities.ecs.EntityComponent;
import atrea.server.game.data.definition.ComponentDefinition;

public abstract class ComponentSystem<T extends EntityComponent> {

    protected T[] components;

    public ComponentSystem() {
    }

    public T getComponent(int id) {
        for (T component : components) {
            if (component != null && component.getId() == id)
                return component;
        }

        return null;
    }

    public abstract void addComponent(ComponentDefinition definition, Entity entity);

    public void removeComponent(int id) {
        components[id] = null;
    }

    public abstract void update();
}