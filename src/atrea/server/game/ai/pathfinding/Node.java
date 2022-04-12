package atrea.server.game.ai.pathfinding;

import atrea.server.engine.utilities.Position;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Node {

    private @Getter Position position;
    private @Getter boolean walkable;
    private @Getter @Setter int hCost, gCost;
    private @Getter @Setter Node parent;

    public Node(Tile tile) {
        this.position = new Position(tile);
        this.walkable = tile.isWalkable();
    }

    public int getFCost() {
        return gCost + hCost;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return position.getX() == node.position.getX()
                && position.getY() == node.position.getY()
                && position.getHeight() == node.position.getHeight();
    }

    @Override public int hashCode() {
        return Objects.hash(position.getX(), position.getY(), position.getHeight());
    }

    @Override public String toString() {
        return "Node{" +
                "x=" + position.getX() +
                ", y=" + position.getY() +
                ", level=" + position.getHeight() +
                '}';
    }
}