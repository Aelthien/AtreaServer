package atrea.server.engine.entities.systems;

import atrea.server.engine.entities.Entity;
import atrea.server.engine.entities.components.MovementComponent;
import atrea.server.engine.entities.components.TransformComponent;
import atrea.server.game.ai.Pathfinder;
import atrea.server.engine.utilities.Position;
import atrea.server.game.data.definition.ComponentDefinition;

public class MovementSystem extends ComponentSystem<MovementComponent> {

    private TransformSystem transformSystem;

    public void moveEntity(int id, Position target, boolean pathfinding) {
        MovementComponent movementComponent = components.get(id);
        TransformComponent transformComponent = transformSystem.get(id);

        movementComponent.setPath(Pathfinder.findPath(transformComponent.getPosition(), target));
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        components.put(entity.getEntityId(), new MovementComponent(entity));
    }

    @Override public void update() {
        for (MovementComponent component : components.values()) {
            if (!component.hasPath())
                continue;

            if (component.getTicksRemaining() == 0)
                transformSystem.get(component.getParent().getEntityId()).setPosition(component.getNextPosition(), false, false);
            else
                component.tick();
        }
    }

    public void initialize(TransformSystem transformSystem) {
        this.transformSystem = transformSystem;
    }
}