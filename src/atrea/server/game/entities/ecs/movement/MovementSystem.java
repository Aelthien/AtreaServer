package atrea.server.game.entities.ecs.movement;

import atrea.server.engine.accounts.CharacterWorldData;
import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.status.StatusComponent;
import atrea.server.game.ai.Pathfinder;
import atrea.server.engine.utilities.Position;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.systems.ComponentSystem;
import atrea.server.game.entities.ecs.status.StatusSystem;
import atrea.server.game.entities.ecs.transform.TransformSystem;

public class MovementSystem extends ComponentSystem<MovementComponent> {

    private TransformSystem transformSystem;
    private StatusSystem statusSystem;

    public MovementSystem() {
        componentsArray = new MovementComponent[5000];
    }

    public void initialize(TransformSystem transformSystem, StatusSystem statusSystem) {
        this.transformSystem = transformSystem;
        this.statusSystem = statusSystem;
    }

    public void moveEntity(int id, Position target, boolean pathfinding) {
        MovementComponent movementComponent = componentsArray[id];

        if (movementComponent.hasPath()) {
            movementComponent.cancelMovement();
        }

        if (pathfinding)
            movementComponent.queuePathPositions(Pathfinder.findPath(transformSystem.getComponent(id).getPosition(), target));
        else
            movementComponent.addPathPosition(target, true);
    }

    public void toggleRun(int id, boolean value) {
        MovementComponent movementComponent = componentsArray[id];
        StatusComponent statusComponent = statusSystem.getComponent(id);

        if (value) {
            if (!movementComponent.isRunning() && statusComponent.getEnergy() > 0)
                movementComponent.setRunning(true);
        } else {
            if (movementComponent.isRunning())
                movementComponent.setRunning(false);
        }
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        componentsArray[entity.getEntityId()] = new MovementComponent(entity);
    }

    @Override public void update() {
        for (MovementComponent movementComponent : componentsArray) {
            if (movementComponent == null)
                continue;

            if (movementComponent.hasPath()) {
                if (movementComponent.isTickingCompleted()) {
                    movementComponent.resetTicksRemaining();

                    int id = movementComponent.getId();
                    MovementTarget nextMove = movementComponent.getNextPosition();
                    boolean bIsRunning = movementComponent.isRunning();
                    boolean bResetQueue = movementComponent.isResetQueue() || nextMove.isTeleport();
                    boolean bPathEnd = !movementComponent.hasPath();

                    transformSystem.setTransform(id, nextMove, bIsRunning, bResetQueue, bPathEnd);

                    movementComponent.setResetQueue(false);
                } else {
                    movementComponent.tick();
                }
            }
        }
    }

    public void moveEntity(int entityId, CharacterWorldData worldData) {
        moveEntity(entityId, worldData.getPosition(), false);
    }

    public void moveEntity(int selfId, int targetId, boolean pathfinding) {
        Position targetPosition = transformSystem.getComponent(targetId).getPosition();

        moveEntity(selfId, targetPosition, pathfinding);
    }

    public void stopEntity(int id) {
        MovementComponent movementComponent = componentsArray[id];

        if (movementComponent.hasPath()) {
            movementComponent.cancelMovement();
        }
    }
}