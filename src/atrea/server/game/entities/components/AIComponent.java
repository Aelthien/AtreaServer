package atrea.server.game.entities.components;

import static atrea.server.game.entities.components.EComponentType.*;

public class AIComponent extends EntityComponent {

    @Override public EComponentType getComponentType() {
        return AI;
    }

    public AIComponent(Entity parent) {
        super(parent);
    }

    @Override public void update() {

    }
}
