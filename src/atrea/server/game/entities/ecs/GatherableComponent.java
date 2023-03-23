package atrea.server.game.entities.ecs;

import static atrea.server.game.entities.ecs.EComponentType.*;

public class GatherableComponent extends EntityComponent {

    @Override public EComponentType getComponentType() {
        return GATHERABLE;
    }

    public GatherableComponent(Entity parent) {
        super(parent);
    }
}