package atrea.server.game.entity.components.systems;

import atrea.server.game.entity.components.StatusComponent;

import java.util.HashMap;
import java.util.Map;

public class StatusSystem extends ComponentSystem {

    private Map<Integer, StatusComponent> components = new HashMap<>();

    private CombatSystem combatSystem;

    public void initialize(CombatSystem combatSystem) {
        this.combatSystem = combatSystem;
    }

    @Override public void update() {

    }
}
