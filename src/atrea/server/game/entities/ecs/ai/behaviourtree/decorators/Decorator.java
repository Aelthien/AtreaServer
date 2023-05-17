package atrea.server.game.entities.ecs.ai.behaviourtree.decorators;

import atrea.server.game.entities.ecs.ai.behaviourtree.BehaviourNode;

public abstract class Decorator implements BehaviourNode {
    protected BehaviourNode child;

    public Decorator(BehaviourNode child) {
        this.child = child;
    }
}