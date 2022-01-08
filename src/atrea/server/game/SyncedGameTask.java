package atrea.game;

import atrea.game.entity.EEntityType;
import atrea.game.world.GameManager;
import com.google.common.base.Preconditions;
import lombok.Getter;

import static atrea.game.entity.EEntityType.*;

public abstract class SyncedGameTask {

    private final @Getter int amount;
    private final @Getter int capacity;
    private final @Getter boolean concurrent;

    public SyncedGameTask(int amount, int capacity, boolean concurrent) {
        this.amount = amount;
        this.capacity = capacity;
        this.concurrent = concurrent;
    }

    public abstract boolean checkIndex(int index);
    public abstract void execute(final int index);
}