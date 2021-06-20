package atrea.game.entity.components.impl;

import atrea.game.entity.Entity;
import atrea.main.Position;
import lombok.Getter;
import lombok.Setter;

public class TransformComponent {

    private @Getter
    Entity parent;

    private @Getter @Setter Position position;
    private @Getter @Setter Position localPosition;
    private @Getter @Setter Position regionPosition;

    private @Getter @Setter float rotation;

    public TransformComponent(Entity parent) {
        this.parent = parent;
        setPosition(new Position());
    }

    public void setPosition(Position position) {
        this.position = position;
        this.regionPosition = new Position(position.getX() / 64, position.getY() / 64);
        this.localPosition = new Position(position.getX() - regionPosition.getX(), position.getY() - regionPosition.getY());
    }
}