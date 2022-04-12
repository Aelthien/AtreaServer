package atrea.server.game.ai.pathfinding;

import lombok.Getter;
import lombok.Setter;

public class Tile {

    private @Getter int x, y;
    private @Getter int level;
    private @Getter @Setter boolean walkable;

    public Tile(int x, int y, int level, int tileType, boolean walkable) {
        this.x = x;
        this.y = y;
        this.level = level;
        //this.type = ETileType.getType(tileType);
        this.walkable = walkable;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}