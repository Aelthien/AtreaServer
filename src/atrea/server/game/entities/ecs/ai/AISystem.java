package atrea.server.game.entities.ecs.ai;

import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.systems.ComponentSystem;

public class AISystem extends ComponentSystem<AIComponent> {

    public AISystem() {
        componentsArray = new AIComponent[5000];
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        componentsArray[entity.getEntityId()] = new AIComponent(definition, entity);
    }

    @Override public void update() {
        for (AIComponent component : componentsArray) {
            if (component == null)
                continue;

            //if (!component.isActive())
                //continue;

            component.getBehaviourTree().update();
        }
    }
}
