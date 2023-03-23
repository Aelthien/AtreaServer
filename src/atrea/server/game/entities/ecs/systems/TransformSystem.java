package atrea.server.game.entities.ecs.systems;

import atrea.server.engine.utilities.Position;
import atrea.server.engine.world.RegionManager;
import atrea.server.game.entities.ecs.Entity;
import atrea.server.game.entities.ecs.TransformComponent;
import atrea.server.engine.main.GameManager;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.movement.MovementComponent;
import atrea.server.game.entities.ecs.movement.MovementTarget;

import java.util.ArrayList;
import java.util.List;

public class TransformSystem extends ComponentSystem<TransformComponent> {

    public TransformSystem() {
        components = new TransformComponent[5000];
    }

    @Override public void update() {
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        components[entity.getEntityId()] = new TransformComponent(entity);
    }

    public boolean isEntityInRange(int entityId, int otherEntityId, int range) {
        TransformComponent transform = components[entityId];
        TransformComponent otherTransform = components[otherEntityId];
        
        return transform.getPosition().isWithinRange(otherTransform.getPosition(), range);
    }

    public void setTransform(int entityId, Position position, boolean teleport, boolean running, boolean resetQueue, boolean pathEnd) {
        components[entityId].setPosition(position, teleport, running, resetQueue, pathEnd);
    }

    public void setTransform(int entityId, MovementTarget target, boolean running, boolean resetQueue, boolean pathEnd) {
        setTransform(entityId, target.getPosition(), target.isTeleport(), running, resetQueue, pathEnd);
    }
}