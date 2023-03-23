package atrea.server.game.entities.ecs.systems;

import atrea.server.game.entities.ecs.Entity;
import atrea.server.game.entities.ecs.StatusComponent;
import atrea.server.game.entities.ecs.movement.MovementComponent;
import atrea.server.game.entities.ecs.TransformComponent;
import atrea.server.game.ai.Pathfinder;
import atrea.server.engine.utilities.Position;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.movement.MovementTarget;

public class MovementSystem extends ComponentSystem<MovementComponent> {

    private TransformSystem transformSystem;
    private StatusSystem statusSystem;

    public MovementSystem() {
        components = new MovementComponent[5000];
    }

    public void initialize(TransformSystem transformSystem, StatusSystem statusSystem) {
        this.transformSystem = transformSystem;
        this.statusSystem = statusSystem;
    }

    public void moveEntity(int id, Position target, boolean pathfinding) {
        MovementComponent movementComponent = components[id];

        movementComponent.setTarget(new MovementTarget(target, !pathfinding));
        movementComponent.setRunning(false);
    }

    public void toggleRun(int id, boolean value) {
        MovementComponent movementComponent = components[id];
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
        components[entity.getEntityId()] = new MovementComponent(entity);
    }

    @Override public void update() {
        for (MovementComponent movementComponent : components) {
            if (movementComponent == null)
                continue;

            TransformComponent transformComponent = transformSystem.getComponent(movementComponent.getId());

            if (movementComponent.hasTarget()) {
                //if (movementComponent.hasPath())
                    //movementComponent.cancelMovement();

                movementComponent.queuePathPositions(Pathfinder.findPath(transformComponent.getPosition(), movementComponent.getTarget().getPosition()));
                movementComponent.setTarget(null);
            }

            if (movementComponent.hasPath()) {
                if (movementComponent.isTickingCompleted()) {
                    movementComponent.resetTicksRemaining();

                    MovementTarget nextMove = movementComponent.getNextPosition();

                    transformSystem.setTransform(movementComponent.getId(), nextMove, false, movementComponent.isResetQueue(), !movementComponent.hasPath());
                } else {
                    movementComponent.tick();
                }
            }
        }
    }
}