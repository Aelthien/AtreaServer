package atrea.game.ai.pathfinding;

import atrea.game.entity.Entity;
import atrea.game.entity.components.EntityComponent;
import atrea.game.entity.components.impl.MovementComponent;
import atrea.game.world.Region;
import atrea.game.world.RegionManager;
import atrea.main.Position;

import java.util.*;

public class PathfinderComponent extends EntityComponent {

    private MovementComponent movementComponent;

    public PathfinderComponent(Entity parent) {
        super(parent);
        this.movementComponent = parent.getComponent(MovementComponent.class);
    }

    public void rebuildGraph() {
        /*for (int x = 0; x < graph.getDimensions(); x++) {
            for (int y = 0; y < graph.getDimensions(); y++) {
                Node node = graph.getNode(x, y);
                //Get walkable variable from map
                //Set walkable on node
                //Update node position
            }
        }*/
    }

    public void cancel() {
        //path = null;
    }

    public void reset() {
        //path = null;
        //Node current = graph.getNode(entityPosition.getX(), entityPosition.getY());
        //Node target = graph.getNode(targetPosition.getX(), targetPosition.getY());

        //findPath(current, target);
    }

    public void findPath(Position targetPosition) {

        if (movementComponent.isMoving())
            movementComponent.cancelMovement();
        else
            movementComponent.allowMovement();

        Region region = RegionManager.getRegion(parent.getTransform().getRegionPosition());

        Node startNode = new Node(region.getTile(parent.getTransform().getLocalPosition()));
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
                reconstructPath(startNode, currentNode);
                return;
            }

            List<Node> neighbours = region.getNeighbours(currentNode);

            for (Node neighbour : neighbours) {
                if (!neighbour.isWalkable() || closed.contains(neighbour))
                    continue;

                int newCost = currentNode.getGCost() + getDistance(currentNode, neighbour);

                if (newCost < neighbour.getGCost() || !open.contains(neighbour)) {
                    neighbour.setGCost(newCost);
                    neighbour.setHCost(getDistance(neighbour, endNode));
                    neighbour.setParent(currentNode);

                    if (!open.contains(neighbour)) {
                        open.add(neighbour);
                    }
                }
            }
        }
    }

    private void reconstructPath(Node start, Node end) {
        List<Position> path = new ArrayList<>();

        Node current = end;

        while (!current.equals(start)) {
            path.add(current.getPosition());
            current = current.getParent();
        }

        Collections.reverse(path);

        System.out.println("Path reconstructed");
        movementComponent.setPath(path);
    }

    private int getDistance(Node current, Node target) {
        int dX = Math.abs(current.getPosition().getX() - target.getPosition().getX());
        int dY = Math.abs(current.getPosition().getY() - target.getPosition().getY());

        if (dX > dY)
            return 14 * dY + 10 * (dX - dY);
        else
            return 14 * dX + 10 * (dY - dX);
    }
}