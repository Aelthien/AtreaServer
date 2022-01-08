package atrea.server.engine.entities.systems;

import atrea.server.engine.entities.Entity;
import atrea.server.engine.entities.components.TransformComponent;
import atrea.server.engine.main.GameManager;
import atrea.server.game.data.definition.ComponentDefinition;

import java.util.ArrayList;
import java.util.List;

public class TransformSystem extends ComponentSystem<TransformComponent> {

    @Override public void update() {
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        components.put(entity.getEntityId(), new TransformComponent(entity));
    }

    public boolean entitiesInRange(Entity entity, Entity other, int range) {
        TransformComponent transform = components.get(entity.getEntityId());
        TransformComponent otherTransform = components.get(other.getEntityId());

        return transform.getPosition().isWithinRange(otherTransform.getPosition(), range);
    }

    public List<Entity> getLocalEntities(TransformComponent transform) {
        List<Entity> localEntities = new ArrayList<>();

        for (var transformComponent : components.values()) {
            if (transformComponent == transform)
                continue;

            if (transformComponent.getPosition().isWithinRange(transform.getPosition(), 50))
                localEntities.add(GameManager.getEntityManager().getEntity(transformComponent.getId()));
        }

        return localEntities;
    }
}