package atrea.server.game.entities;

import atrea.server.engine.utilities.Position;
import atrea.server.game.entities.ecs.command.EntityCommand;

public class MoveToTileCommand extends EntityCommand {

    private Position target;
    private boolean teleport;

    public MoveToTileCommand(int delay) {
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
