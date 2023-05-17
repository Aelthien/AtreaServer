package atrea.server.game.entities.ecs;

import atrea.server.game.entities.Entity;
import lombok.Getter;
import lombok.Setter;

public abstract class EntityComponent {

    protected @Getter int id;
    protected @Getter @Setter
    Entity parent;
    protected @Getter @Setter boolean updated;
    protected int ticksRemaining;
    protected @Setter int tickRate;
    private @Getter @Setter boolean active;

    public abstract EComponentType getComponentType();

    public EntityComponent(Entity parent) {
        this.id = parent.getEntityId();
        this.parent = parent;
        parent.addComponent(getComponentType());
    }

    public void tick() {
        ticksRemaining--;
    }

    public void resetTicksRemaining(int tickRate) {
        this.tickRate = tickRate;
        resetTicksRemaining();
    }

    public void resetTicksRemaining() {
        ticksRemaining = tickRate;
    }

    public boolean isTickingCompleted() {
        return ticksRemaining == 0;
    }

    public void setNeedsUpdating() {
        parent.addUpdateFlag(getComponentType());
    }

    public abstract void reset();
}