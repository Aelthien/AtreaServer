package atrea.server.game.entities.ecs.status;

import atrea.server.game.entities.Entity;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.combat.CombatSystem;
import atrea.server.game.entities.ecs.systems.ComponentSystem;

public class StatusSystem extends ComponentSystem<StatusComponent> {

    private CombatSystem combatSystem;

    public StatusSystem() {
        componentsArray = new StatusComponent[5000];
    }

    public void initialize(CombatSystem combatSystem) {
        this.combatSystem = combatSystem;
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        componentsArray[entity.getEntityId()] = new StatusComponent(entity);
    }

    @Override public void update() {
        for (var status : componentsArray) {
            if (status != null)
                status.tick();
        }
    }
}