package atrea.game.entity.components.impl;

import atrea.game.entity.Entity;
import atrea.game.entity.components.EntityComponent;
import atrea.main.Position;
import atrea.server.PlayerSessions;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class MovementComponent extends EntityComponent {

    private @Getter @Setter Queue<Position> positions = new ArrayDeque<>();
    private @Getter Position target;
    private int ticksSinceLastMovement;
    private int ticksPerMovement = 2;
    private @Getter boolean moving;
    private boolean cancelCurrentMovement;

    public MovementComponent(Entity parent) {
        super(parent);

        target = parent.getTransform().getPosition();
    }

    public void cancelMovement() {
        positions.clear();
        moving = false;
        cancelCurrentMovement = true;
    }

    public void allowMovement() {
        cancelCurrentMovement = false;
    }

    public void endMove() {
        ticksSinceLastMovement = 0;
    }

    @Override
    public void update() {
        if (positions.isEmpty())
            return;

        if (ticksSinceLastMovement == ticksPerMovement) {
            ticksSinceLastMovement = 0;

            if (!cancelCurrentMovement) {
                target = positions.poll();

                parent.getTransform().setPosition(target);
                PlayerSessions.getPlayerSession(parent).getMessageSender().sendMove(target, false, false);
            }
        }

        ticksSinceLastMovement++;
    }

    public void setPath(List<Position> positions) {
        updated = true;
        this.positions.addAll(positions);
    }
}