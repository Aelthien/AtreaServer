package atrea.server.game.entities.ecs.systems;

import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.Entity;
import atrea.server.game.entities.ecs.GrowthComponent;

public class GrowthSystem extends ComponentSystem<GrowthComponent> {
    @Override public void addComponent(ComponentDefinition definition, Entity entity) {

    }

    @Override public void update() {
        for (GrowthComponent component : components) {
        }
    }
}