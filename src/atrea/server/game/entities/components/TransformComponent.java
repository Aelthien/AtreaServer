package atrea.server.engine.entities.components;

import atrea.server.engine.entities.Entity;
import atrea.server.engine.utilities.Position;
import atrea.server.game.entities.components.EFacing;
import lombok.Getter;
import lombok.Setter;

import static atrea.server.engine.entities.components.EComponentType.*;

@Getter @Setter
public class TransformComponent extends EntityComponent {

    private Position position;
    private boolean teleport;
    private boolean pathEnd;
    private boolean running;
    private EFacing facing;

    @Override public EComponentType getComponentType() {
        return TRANSFORM;
    }

    public TransformComponent(Entity parent) {
        super(parent);

        this.position = new Position();
    }

    public TransformComponent setPosition(Position position, boolean teleport, boolean pathEnd) {
        this.position = position;
        this.teleport = teleport;
        this.pathEnd = pathEnd;

        return this;
    }

    public Position getRegionPosition() {
        return new Position(position.getX() / 64, position.getY() / 64, position.getHeight());
    }

    public Position getLocalPosition() {
        Position regionPosition = getRegionPosition();
        return new Position(position.getX() - regionPosition.getX(), position.getY() - regionPosition.getY(), position.getHeight());
    }
}