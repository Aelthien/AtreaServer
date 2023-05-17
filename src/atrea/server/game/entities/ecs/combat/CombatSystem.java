package atrea.server.game.entities.ecs.combat;

import atrea.server.game.entities.Entity;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.systems.ComponentSystem;
import atrea.server.game.entities.ecs.status.StatusSystem;

import java.util.HashMap;
import java.util.Map;

public class CombatSystem extends ComponentSystem<CombatComponent> {

    private Map<Integer, CombatComponent> components = new HashMap<>();

    private StatusSystem statusSystem;

    public void initialize(StatusSystem statusSystem) {
        this.statusSystem = statusSystem;
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        components.put(entity.getEntityId(), new CombatComponent(entity));
    }

    @Override public void update() {

    }
}