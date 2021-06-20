package atrea.game;

import atrea.game.entity.EEntityType;
import atrea.game.world.World;
import com.google.common.base.Preconditions;
import lombok.Getter;

import static atrea.game.entity.EEntityType.*;

public abstract class SyncedGameTask {

    private final @Getter int amount;
    private final @Getter int capacity;
    private final @Getter boolean concurrent;
    private final @Getter EEntityType entityType;

    public SyncedGameTask(int amount, int capacity, boolean concurrent, EEntityType entityType) {
        Preconditions.checkArgument(entityType == Player || entityType == NPC, "Invalid node type.");
        this.amount = entityType == Player ? World.getPlayers().getSize() : World.getNpcs().getSize();
        this.capacity = entityType == Player ? World.getPlayers().getSize() : World.getNpcs().getSize();;
        this.concurrent = concurrent;
        this.entityType = entityType;
    }


    public SyncedGameTask(EEntityType entityType, boolean concurrent) {
        Preconditions.checkArgument(entityType == Player || entityType == NPC, "Invalid node type.");
        this.amount = entityType == Player ? World.getPlayers().getSize() : World.getNpcs().getSize();
        this.capacity = entityType == Player ? World.getPlayers().getCapacity() : World.getNpcs().getCapacity();
        this.entityType = entityType;
        this.concurrent = concurrent;
    }

    public boolean checkIndex(int index) {
        return entityType == Player ? World.getPlayers().get(index) != null : World.getNpcs().get(index) != null;
    }

    public abstract void execute(final int index);
}