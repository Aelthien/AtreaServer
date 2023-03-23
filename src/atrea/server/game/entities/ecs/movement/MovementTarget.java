package atrea.server.game.entities.ecs.movement;

import atrea.server.engine.utilities.Position;
import lombok.Getter;

public class MovementTarget {

    public MovementTarget(Position position, boolean teleport) {
        this.position = position;
        this.teleport = teleport;
    }

    private @Getter Position position;
    private @Getter boolean teleport;
}