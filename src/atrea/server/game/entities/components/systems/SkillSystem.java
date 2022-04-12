package atrea.server.game.entities.components.systems;

import atrea.server.game.entities.components.Entity;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.components.SkillComponent;

public class SkillSystem extends ComponentSystem<SkillComponent> {

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        components.put(entity.getEntityId(), new SkillComponent(entity));
    }

    @Override public void update() {

    }
}