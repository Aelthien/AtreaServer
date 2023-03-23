package atrea.server.game.entities.ecs.command;

import lombok.Getter;

public abstract class Script {

    private @Getter boolean completed;
    private boolean active;
    protected int duration;
    private int currentTime;
    protected boolean runEveryTick;
    protected boolean looping;

    public Script(String args) {
        setUpScript(args);
    }

    public void tick() {
        if (currentTime == duration) {
            completed = true;
            active = false;
        }

        if (runEveryTick)
            script();
        else if (completed) {
            script();

            if (!looping)
                return;
        }

        currentTime++;
    }

    public abstract void setUpScript(String args);

    public abstract void script();
}