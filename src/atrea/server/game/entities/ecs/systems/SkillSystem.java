package atrea.server.game.entities.ecs.systems;

import atrea.server.game.entities.ecs.Entity;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.SkillComponent;

public class SkillSystem extends ComponentSystem<SkillComponent> {

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        components[entity.getEntityId()] = new SkillComponent(entity);
    }

    @Override public void update() {

    }
}