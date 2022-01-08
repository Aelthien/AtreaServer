package atrea.server.game.entity.components.systems;

import atrea.server.game.entity.components.CombatComponent;

import java.util.HashMap;
import java.util.Map;

public class CombatSystem extends ComponentSystem {

    private Map<Integer, CombatComponent> components = new HashMap<>();

    private StatusSystem statusSystem;

    public void initialize(StatusSystem statusSystem) {
        this.statusSystem = statusSystem;
    }

    @Override public void update() {

    }
}