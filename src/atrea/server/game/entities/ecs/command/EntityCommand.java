package atrea.server.game.entities.ecs.command;

import lombok.Getter;

public abstract class EntityCommand {

    protected @Getter int delay;
    protected @Getter boolean completed;

    private int delayTimer;

    public EntityCommand(int delay) {
        this.delay = delay;
    }

    public abstract void setData(String data);

    public abstract int getCode();

    public abstract void execute();

    public void tick() {
        if (delayTimer == 0) {
            execute();
            completed = true;
        }

        delayTimer--;
    }
}