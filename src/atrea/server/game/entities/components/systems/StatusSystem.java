package atrea.server.game.entities.components.systems;

import atrea.server.game.entities.components.Entity;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.components.StatusComponent;

public class StatusSystem extends ComponentSystem<StatusComponent> {

    private CombatSystem combatSystem;

    public void initialize(CombatSystem combatSystem) {
        this.combatSystem = combatSystem;
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        components.put(entity.getEntityId(), new StatusComponent(entity));
    }

    @Override public void update() {
        for (var status : components.values()) {
            status.tick();
        }
    }
}