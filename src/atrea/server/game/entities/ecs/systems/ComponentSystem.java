package atrea.server.game.entities.ecs.systems;

import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.EntityComponent;
import atrea.server.game.data.definition.ComponentDefinition;

import java.util.Map;

public abstract class ComponentSystem<T extends EntityComponent> {

    protected T[] componentsArray;
    protected Map<Integer, T> componentsMap;

    public ComponentSystem() {
    }

    public T getComponent(int id) {
        return componentsArray[id];
    }

    public abstract void addComponent(ComponentDefinition definition, Entity entity);

    public void removeComponent(int id) {
        componentsArray[id] = null;
    }

    public abstract void update();

    public void reset()
    {
        for (T component : componentsArray) {
            if (component != null)
                component.reset();
        }
    }
}