package atrea.server.game.entity.components.systems;

import atrea.server.game.entity.components.SkillComponent;

import java.util.HashMap;
import java.util.Map;

public class SkillSystem extends ComponentSystem {

    private Map<Integer, SkillComponent> components = new HashMap<>();

    public SkillComponent getSkillComponent(int id) {
        return components.get(id);
    }

    @Override public void update() {

    }
}