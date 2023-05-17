package atrea.server.game.entities.ecs.movement;

import atrea.server.engine.utilities.Position;
import atrea.server.game.entities.ecs.EComponentType;
import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.EntityComponent;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import static atrea.server.game.entities.ecs.EComponentType.*;

public class MovementComponent extends EntityComponent {

    private @Getter @Setter Queue<MovementTarget> movementQueue = new ArrayDeque<>();
    private @Getter @Setter MovementTarget target;
    private @Getter @Setter boolean resetQueue;
    private @Getter boolean running;

    @Override
    public EComponentType getComponentType() {
        return MOVEMENT;
    }

    public MovementComponent(Entity parent) {
        super(parent);
    }

    public boolean hasPath() {
        return !movementQueue.isEmpty();
    }

    public void update() {
        ticksRemaining--;
    }

    public void cancelMovement() {
        movementQueue.clear();
        resetQueue = true;
    }

    public void setRunning(boolean running) {
        this.running = running;
        this.tickRate = 1;
    }

    public boolean hasTarget() {
        return target != null;
    }

    public void queuePathPositions(List<Position> movementPositions) {
        for (int i = 0; i < movementPositions.size(); i++) {
            movementQueue.add(new MovementTarget(movementPositions.get(i), false));
        }

        parent.addUpdateFlag(getComponentType());
    }

    public MovementTarget getNextPosition() {
        target = movementQueue.poll();

        return target;
    }

    public void reset() {
        resetQueue = false;
    }

    public void addPathPosition(Position target, boolean bTeleport) {
        movementQueue.add(new MovementTarget(target, bTeleport));
    }
}