package atrea.server.engine.entities.components;

import atrea.server.engine.entities.Entity;

import static atrea.server.engine.entities.components.EComponentType.*;

public class AIComponent extends EntityComponent {

    @Override public EComponentType getComponentType() {
        return AI;
    }

    public AIComponent(Entity parent) {
        super(parent);
    }
}
