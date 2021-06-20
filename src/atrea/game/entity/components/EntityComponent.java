package atrea.game.entity.components;

import atrea.game.entity.Entity;
import lombok.Getter;
import lombok.Setter;

public class EntityComponent {

    protected @Getter @Setter Entity parent;
    protected @Getter @Setter boolean updated;

    public EntityComponent(Entity parent) {
        this.parent = parent;
    }

    public void update() {
    }
}