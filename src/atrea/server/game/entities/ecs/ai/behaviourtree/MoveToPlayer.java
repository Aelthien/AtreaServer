package atrea.server.game.entities.ecs.ai.behaviourtree;

import atrea.server.game.entities.ecs.ai.behaviourtree.tasks.MoveToTask;
import atrea.server.game.entities.ecs.transform.TransformComponent;

public class MoveToPlayer extends BehaviourTree {
    private TransformComponent targetTransform;

    public MoveToPlayer(Blackboard blackboard) {
        super(blackboard);
    }

    public void setTargetTransform(TransformComponent targetTransform) {
        this.targetTransform = targetTransform;

        blackboard.setPositionValue("targetPosition", targetTransform.getPosition());

        traverse(root, node -> {
            if (node instanceof MoveToTask) {
                ((MoveToTask) node).setTargetTransform(targetTransform);
            }
        });
    }
}
