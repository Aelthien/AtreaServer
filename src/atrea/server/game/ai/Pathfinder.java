package atrea.server.game.ai;

import atrea.server.engine.utilities.Position;
import atrea.server.engine.world.Region;
import atrea.server.engine.world.RegionManager;
import atrea.server.game.ai.pathfinding.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pathfinder {
    public static List<Position> findPath(Position startPosition, Position targetPosition) {
        Region region = RegionManager.getRegion(new Position(targetPosition.getX() / 64, targetPosition.getY() / 64, targetPosition.getHeight()));

        Node startNode = new Node(region.getTile(startPosition));
        Node endNode = new Node(region.getTile(targetPosition));

        List<Node> closed = new ArrayList<>();
        List<Node> open = new ArrayList<>();

        open.add(startNode);

        while (open.size() > 0) {
            Node currentNode = open.get(0);
            for (int i = 1; i < open.size(); i++) {
                if (open.get(i).getFCost() < currentNode.getFCost()
                        || open.get(i).getFCost() == currentNode.getFCost()
                        && open.get(i).getHCost() < currentNode.getHCost()) {
                    currentNode = open.get(i);
                }
            }

            open.remove(currentNode);
            closed.add(currentNode);

            if (currentNode.equals(endNode)) {
                return reconstructPath(startNode, currentNode);
            }

            List<Node> neighbours = region.getNeighbours(currentNode);

            for (Node neighbour : neighbours) {
                if (!neighbour.isWalkable() || closed.contains(neighbour))
                    continue;

                int newCost = currentNode.getGCost() + currentNode.getPosition().calculateTileDistance(neighbour.getPosition());

                if (newCost < neighbour.getGCost() || !open.contains(neighbour)) {
                    neighbour.setGCost(newCost);
                    neighbour.setHCost(neighbour.getPosition().calculateTileDistance(endNode.getPosition()));
                    neighbour.setParent(currentNode);

                    if (!open.contains(neighbour)) {
                        open.add(neighbour);
                    }
                }
            }
        }

        return null;
    }

    private static List<Position> reconstructPath(Node start, Node end) {
        List<Position> positions = new ArrayList<>();

        Node current = end;

        while (!current.equals(start)) {
            positions.add(current.getPosition());
            current = current.getParent();
        }

        Collections.reverse(positions);

        return positions;
    }
}
