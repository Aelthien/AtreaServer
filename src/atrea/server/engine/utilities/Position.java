package atrea.server.utilities;

import atrea.server.game.ai.pathfinding.Tile;
import lombok.Getter;
import lombok.Setter;

public class Position {

    private @Getter int x, y;
    private @Getter @Setter int level;

    public Position() {
        this.x = 0;
        this.y = 0;
        this.level = 0;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.level = 0;
    }

    public Position(int x, int y, int level) {
        this.x = x;
        this.y = y;
        this.level = level;
    }

    public Position(Tile tile) {
        this.x = tile.getX();
        this.y = tile.getY();
        this.level = tile.getLevel();
    }

    public boolean isWithinRange(Position otherPosition, int range) {
        int distance = (int) Math.sqrt(Math.pow(otherPosition.x - x, 2) + Math.pow(otherPosition.y - y, 2));

        return distance < range;
    }

    @Override public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", level=" + level +
                '}';
    }
}