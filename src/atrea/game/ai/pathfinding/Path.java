package atrea.game.ai.pathfinding;

import atrea.main.Position;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Queue;

public class Path
{
    private @Getter @Setter Queue<Position> positions;
    private @Getter PathfinderComponent pathfinder;

    public Path(PathfinderComponent pathfinder, List<Position> positions) {

        this.pathfinder = pathfinder;
        positions.addAll(positions);
    }

    public void pathBlocked()
    {
        pathfinder.reset();
    }

    public boolean isPathEnd() {
        return positions.isEmpty() ? true : false;
    }

    public Position getNextPosition() {
        return positions.poll();
    }
}