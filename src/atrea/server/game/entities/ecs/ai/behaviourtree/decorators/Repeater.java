package atrea.server.game.entities.ecs.ai.behaviourtree.decorators;

import atrea.server.game.entities.ecs.ai.behaviourtree.BehaviourNode;
import atrea.server.game.entities.ecs.ai.behaviourtree.EBehaviourStatus;

import static atrea.server.game.entities.ecs.ai.behaviourtree.EBehaviourStatus.*;

public class Repeater extends Decorator {
    private int ticks;
    private int currentTick;
    private EBehaviourStatus childStatus;

    public Repeater(BehaviourNode child, int ticks) {
        super(child);

        this.ticks = ticks;
        this.childStatus = RUNNING;
    }

    @Override public EBehaviourStatus execute() {
        if (childStatus == RUNNING) {
            childStatus = child.execute();
        }

        if (childStatus != RUNNING) {
            currentTick++;

            if (currentTick >= ticks) {
                currentTick = 0;
                childStatus = RUNNING;

                return SUCCESS;
            } else
                childStatus = RUNNING;
        }

        return RUNNING;
    }
}