package atrea.server.game.entities.ecs.ai.behaviourtree.composites;

import atrea.server.game.entities.ecs.ai.behaviourtree.BehaviourNode;
import atrea.server.game.entities.ecs.ai.behaviourtree.EBehaviourStatus;

import static atrea.server.game.entities.ecs.ai.behaviourtree.EBehaviourStatus.*;

public class Selector extends Composite {

    @Override public EBehaviourStatus execute() {
        for (BehaviourNode child : children) {
            EBehaviourStatus status = child.execute();

            if (status != FAILURE) {
                return status;
            }
        }

        return FAILURE;
    }
}