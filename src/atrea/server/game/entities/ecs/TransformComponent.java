package atrea.server.game.entities.ecs;

import atrea.server.engine.utilities.Position;
import atrea.server.engine.world.RegionManager;
import atrea.server.game.entities.ecs.movement.MovementTarget;
import lombok.Getter;
import lombok.Setter;

import static atrea.server.game.entities.ecs.EComponentType.*;

@Getter @Setter
public class TransformComponent extends EntityComponent {

    private Position position;
    private boolean teleport;
    private boolean pathEnd;
    private boolean running;
    private boolean resetQueue;
    private EFacing facing;

    @Override public EComponentType getComponentType() {
        return TRANSFORM;
    }

    public TransformComponent(Entity parent) {
        super(parent);

        this.position = new Position();
    }

    public void setPosition(Position position) {
        setPosition(position, true, false, true, true);

        RegionManager.getEntityGrid().insert(position, parent.getEntityId());
    }

    public void setPosition(Position position, boolean teleport, boolean running, boolean resetQueue, boolean pathEnd) {
        this.position = position;
        this.teleport = teleport;
        this.running = running;
        this.resetQueue = resetQueue;
        this.pathEnd = pathEnd;

        parent.addUpdateFlag(getComponentType());
    }

    public Position getRegionPosition() {
        return new Position(position.getX() / 64, position.getY() / 64, position.getHeight());
    }

    public Position getLocalPosition() {
        Position regionPosition = getRegionPosition();
        return new Position(position.getX() - regionPosition.getX(), position.getY() - regionPosition.getY(), position.getHeight());
    }
}