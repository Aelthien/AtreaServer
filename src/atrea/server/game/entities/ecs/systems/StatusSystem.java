package atrea.server.game.entities.ecs.systems;

import atrea.server.game.entities.ecs.Entity;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.StatusComponent;

public class StatusSystem extends ComponentSystem<StatusComponent> {

    private CombatSystem combatSystem;

    public StatusSystem() {
        components = new StatusComponent[5000];
    }

    public void initialize(CombatSystem combatSystem) {
        this.combatSystem = combatSystem;
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        components[entity.getEntityId()] = new StatusComponent(entity);
    }

    @Override public void update() {
        for (var status : components) {
            if (status != null)
                status.tick();
        }
    }
}