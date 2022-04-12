package atrea.server.game.entities.components;

import lombok.Getter;
import lombok.Setter;

public abstract class EntityComponent {

    protected @Getter int id;
    protected @Getter @Setter Entity parent;
    protected @Getter @Setter boolean updated;

    public abstract EComponentType getComponentType();

    public EntityComponent(Entity parent) {
        this.id = parent.getEntityId();
        this.parent = parent;
        parent.addComponent(getComponentType());
    }

    public abstract void update();

    public void setNeedsUpdating() {
        parent.addUpdateFlag(getComponentType());
    }
}