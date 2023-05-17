package atrea.server.game.entities.ecs.transform;

import atrea.server.engine.utilities.Position;
import atrea.server.engine.world.RegionManager;
import atrea.server.game.entities.Entity;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.movement.MovementTarget;
import atrea.server.game.entities.ecs.systems.ComponentSystem;

import java.util.List;

public class TransformSystem extends ComponentSystem<TransformComponent> {

    public TransformSystem() {
        componentsArray = new TransformComponent[5000];
    }

    @Override public void update() {
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        componentsArray[entity.getEntityId()] = new TransformComponent(entity);
    }

    public boolean isEntityInRange(int entityId, int otherEntityId, int range) {
        TransformComponent transform = componentsArray[entityId];
        TransformComponent otherTransform = componentsArray[otherEntityId];
        
        return transform.getPosition().isWithinRange(otherTransform.getPosition(), range);
    }

    public void setTransform(int entityId, Position position, boolean teleport, boolean running, boolean resetQueue, boolean pathEnd) {
        componentsArray[entityId].setPosition(position, teleport, running, resetQueue, pathEnd);
    }

    public void setTransform(int entityId, MovementTarget target, boolean running, boolean resetQueue, boolean pathEnd) {
        setTransform(entityId, target.getPosition(), target.isTeleport(), running, resetQueue, pathEnd);
    }

    public Position getRandomPositionInRange(int entityId, int radius) {
        TransformComponent transform = componentsArray[entityId];

        List<Position> positions = RegionManager.getValidPositionsInRange(transform.getPosition(), radius);

        return positions.get((int) (Math.random() * positions.size()));
    }
}