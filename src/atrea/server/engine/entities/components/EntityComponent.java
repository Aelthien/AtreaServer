package atrea.server.game.entities.components;

import atrea.server.engine.entities.Entity;
import lombok.Getter;
import lombok.Setter;

public class EntityComponent {

    protected @Getter int id;
    protected @Getter @Setter Entity parent;
    protected @Getter @Setter boolean updated;

    public EntityComponent(Entity parent) {
        this.id = parent.getUid();
        this.parent = parent;
    }
}