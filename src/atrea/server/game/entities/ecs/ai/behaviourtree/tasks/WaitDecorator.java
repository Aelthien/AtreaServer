package atrea.server.game.entities.ecs.ai.behaviourtree.tasks;

import atrea.server.game.entities.ecs.ai.behaviourtree.BehaviourNode;
import atrea.server.game.entities.ecs.ai.behaviourtree.EBehaviourStatus;
import atrea.server.game.entities.ecs.ai.behaviourtree.decorators.Decorator;

public class WaitDecorator extends Decorator {
    private int ticks;
    private int currentTick;

    public WaitDecorator(BehaviourNode child, int ticks) {
        super(child);

        this.ticks = ticks;
    }


    @Override public EBehaviourStatus execute() {
        if (currentTick < ticks) {
            currentTick++;

            return EBehaviourStatus.RUNNING;
        }

        currentTick = 0;

        return EBehaviourStatus.SUCCESS;
    }
}