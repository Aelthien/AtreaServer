package atrea.server.game;

import lombok.Getter;

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