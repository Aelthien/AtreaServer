package atrea.server.game.entities.ecs.ai.behaviourtree.composites;

import atrea.server.game.entities.ecs.ai.behaviourtree.BehaviourNode;
import atrea.server.game.entities.ecs.ai.behaviourtree.EBehaviourStatus;

import static atrea.server.game.entities.ecs.ai.behaviourtree.EBehaviourStatus.SUCCESS;

public class Sequence extends Composite {
        @Override public EBehaviourStatus execute() {
            for (BehaviourNode child : children) {
                EBehaviourStatus status = child.execute();

                if (status != SUCCESS) {
                    return status;
                }
            }

            return SUCCESS;
        }
}
