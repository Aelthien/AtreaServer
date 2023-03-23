package atrea.server.engine.world;

import atrea.server.game.ai.pathfinding.Node;
import atrea.server.game.ai.pathfinding.Tile;
import atrea.server.engine.utilities.Position;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Region {

    private int REGION_SIZE = 64;

    private @Getter int id;
    private @Getter int mapId;
    private @Getter @Setter boolean loaded;
    private @Getter Position position;

    private @Getter Tile[][][] tiles;

    public Region(int id, int mapId, int x, int y, Tile[][][] tiles, EntitySpawnData[] entitySpawns) {
        this.id = id;
        this.mapId = mapId;
        this.tiles = tiles;

        this.position = new Position(x, y);
    }

    public Tile[][] getTiles(int level) {
        return tiles[level];
    }

    public Tile getTile(int x, int y, int level) {
        //if (x >= 0 && y >= 0 && x < 64 && y < 64)
            return tiles[level][x][y];

        //return null;
    }

    public Tile getTile(Position position) {
        return getTile(position.getX(), position.getY(), position.getHeight());
    }

    public List<Node> getNeighbours(Node node) {

        List<Node> neighbours = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0)
                    continue;

                int checkX = node.getPosition().getX() + x;
                int checkY = node.getPosition().getY() + y;

                if (checkX >= 0 && checkX < REGION_SIZE && checkY >= 0 && checkY < REGION_SIZE) {
                    Node neighbour = new Node(getTile(checkX, checkY, node.getPosition().getHeight()));
                    neighbours.add(neighbour);
                }
            }
        }

        return neighbours;
    }
}