package atrea.server.engine.utilities;

import lombok.Getter;
import lombok.Setter;

import atrea.server.game.ai.pathfinding.Tile;

@Getter @Setter
public class Position {

    private int x, y;
    private int height;

    public Position() {
        this.x = 0;
        this.y = 0;
        this.height = 0;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.height = 0;
    }

    public Position(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
    }

    public Position(Tile tile) {
        this.x = tile.getX();
        this.y = tile.getY();
        this.height = tile.getLevel();
    }

    public boolean isWithinRange(Position position, int range) {
        int tileDistance = calculateTileDistance(position);

        return tileDistance <= range;
    }

    public int calculateTileDistance(Position position) {
        int dX = Math.abs(this.x - position.x);
        int dY = Math.abs(this.y - position.y);

        if (dX > dY)
            return 14 * dY + 10 * (dX - dY);
        else
            return 14 * dX + 10 * (dY - dX);
    }

    @Override public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", level=" + height +
                '}';
    }

    public Position clone() {
        return new Position(x, y, height);
    }
}