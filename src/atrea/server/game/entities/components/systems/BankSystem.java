package atrea.server.game.entity.components.systems;

import atrea.server.game.entity.components.EntityComponent;
import atrea.server.game.entity.components.BankComponent;

import java.util.HashMap;
import java.util.Map;

public class BankSystem extends ComponentSystem {

    private Map<Integer, BankComponent> components = new HashMap<>();

    @Override
    public void addComponent(EntityComponent component) {
        components.put(component.getParent().getId(), (BankComponent) component);
    }

    @Override public void update() {

    }
}
