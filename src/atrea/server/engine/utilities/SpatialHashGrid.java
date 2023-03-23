package atrea.server.engine.utilities;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class SpatialHashGrid {

    private @Getter int cellSize;
    private @Getter int width;
    private @Getter int height;
    private @Getter List<Integer>[][] grid;

    public SpatialHashGrid(int cellSize, int width, int height) {
        this.cellSize = cellSize;
        this.width = width;
        this.height = height;
        this.grid = new ArrayList[width][height];
    }

    public void insert(Position position, int value) {
        int cellX = position.getX() / cellSize;
        int cellY = position.getY() / cellSize;

        List<Integer> list = grid[cellX][cellY];

        if (list == null) {
            list = new ArrayList<>();
            grid[cellX][cellY] = list;
        }

        if (!list.contains(value))
            list.add(value);
    }

    public void remove(Position position, int value) {
        int cellX = position.getX() / cellSize;
        int cellY = position.getY() / cellSize;

        grid[cellX][cellY].remove(value);
    }

    public List<Integer> retrieve(Position position) {
        int cellX = position.getX() / cellSize;
        int cellY = position.getY() / cellSize;

        return grid[cellX][cellY];
    }
}