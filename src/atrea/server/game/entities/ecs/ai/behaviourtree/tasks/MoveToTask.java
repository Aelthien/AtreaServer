package atrea.server.game.entities.ecs.ai.behaviourtree.tasks;

import atrea.server.engine.main.GameManager;
import atrea.server.engine.utilities.Position;
import atrea.server.game.entities.ecs.ai.behaviourtree.EBehaviourStatus;
import atrea.server.game.entities.ecs.movement.MovementSystem;
import atrea.server.game.entities.ecs.transform.TransformComponent;
import lombok.Getter;
import lombok.Setter;

public class MoveToTask extends Task {
    private @Getter @Setter TransformComponent selfTransform;
    private @Getter @Setter TransformComponent targetTransform;
    private @Getter @Setter int tileRadius;
    private boolean isMoving;
    private Position lastTargetPosition;

    public MoveToTask(TransformComponent selfTransform, TransformComponent targetTransform, int tileRadius) {
        super();

        this.selfTransform = selfTransform;
        this.targetTransform = targetTransform;
        this.tileRadius = tileRadius;
    }

    @Override public EBehaviourStatus execute() {
        MovementSystem movementSystem = GameManager.getSystemManager().getMovementSystem();

        int distance = selfTransform.getPosition().calculateTileDistance(targetTransform.getPosition());

        if (distance <= tileRadius) {
            movementSystem.stopEntity(selfTransform.getId());

            return EBehaviourStatus.SUCCESS;
        }
        else if (!isMoving || !lastTargetPosition.equals(targetTransform.getPosition())) {
            Position targetPosition = targetTransform.getPosition();

            movementSystem.moveEntity(selfTransform.getId(), targetPosition, true);

            lastTargetPosition = targetTransform.getPosition();
        }

        return EBehaviourStatus.SUCCESS;
    }
}
