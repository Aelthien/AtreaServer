package atrea.server.game.entities.components.systems;

import atrea.server.game.entities.components.Entity;
import atrea.server.game.entities.components.EntityComponent;
import atrea.server.game.data.definition.ComponentDefinition;

import java.util.HashMap;
import java.util.Map;

public abstract class ComponentSystem<T extends EntityComponent> {

    protected Map<Integer, T> components = new HashMap<>();

    public ComponentSystem() {

    }

    public T get(int id) {
        return components.get(id);
    }

    public abstract void addComponent(ComponentDefinition definition, Entity entity);

    public void removeComponent(int id) {
        components.remove(id);
    }

    public abstract void update();
}