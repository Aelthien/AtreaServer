package atrea.server.game.entities.ecs.command;

public class AnimationCommand extends EntityCommand {

    public AnimationCommand(int delay, boolean loop, int animation) {
        super(delay);
    }

    @Override public void setData(String data) {

    }

    @Override public int getCode() {
        return 0;
    }

    @Override public void execute() {

    }
}
