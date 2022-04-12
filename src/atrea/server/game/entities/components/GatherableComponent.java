package atrea.server.game.entities.components;

import static atrea.server.game.entities.components.EComponentType.*;

public class GatherableComponent extends EntityComponent {

    @Override public EComponentType getComponentType() {
        return GATHERABLE;
    }

    public GatherableComponent(Entity parent) {
        super(parent);
    }

    @Override public void update() {

    }
}