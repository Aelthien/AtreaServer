package atrea.server.game.entities.ecs;

import static atrea.server.game.entities.ecs.EComponentType.*;

public class AIComponent extends EntityComponent {

    @Override public EComponentType getComponentType() {
        return AI;
    }

    public AIComponent(Entity parent) {
        super(parent);
    }
}
