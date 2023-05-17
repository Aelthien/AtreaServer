package atrea.server.game.entities.ecs;

import atrea.server.game.entities.Entity;

import static atrea.server.game.entities.ecs.EComponentType.*;

public class GatherableComponent extends EntityComponent {

    @Override public EComponentType getComponentType() {
        return GATHERABLE;
    }

    public GatherableComponent(Entity parent) {
        super(parent);
    }

    @Override public void reset() {

    }
}