package atrea.server.game.entities.components;

import atrea.server.engine.utilities.Position;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import static atrea.server.game.entities.components.EComponentType.*;

public class MovementComponent extends EntityComponent {

    private @Getter @Setter Queue<Position> pathPositions = new ArrayDeque<>();
    private @Getter int ticksRemaining;
    private int ticksPerMovement = 2;
    private @Getter boolean moving;
    private @Getter @Setter boolean running;
    private boolean cancelCurrentMovement;

    @Override public EComponentType getComponentType() {
        return MOVEMENT;
    }

    public MovementComponent(Entity parent) {
        super(parent);
    }

    public boolean hasPath() {
        return !pathPositions.isEmpty();
    }

    public void update() {
        if (ticksRemaining == 0) {
            ticksRemaining = ticksPerMovement;
            return;
        }

        ticksRemaining--;
    }

    public void cancelMovement() {
        pathPositions.clear();
        moving = false;
        cancelCurrentMovement = true;
    }

    public void allowMovement() {
        cancelCurrentMovement = false;
    }

    public void endMove() {
        //ticksSinceLastMovement = 0;
    }

    public void setPath(List<Position> pathPositions) {
        this.pathPositions.addAll(pathPositions);

        parent.addUpdateFlag(getComponentType());
    }

    public Position getNextPosition() {
        Position position = pathPositions.poll();
        return position;
    }
}