package atrea.server.game.entities.ecs.ai.behaviourtree.composites;

import atrea.server.game.entities.ecs.ai.behaviourtree.BehaviourNode;
import atrea.server.game.entities.ecs.ai.behaviourtree.EBehaviourStatus;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Composite implements BehaviourNode {
    protected @Getter List<BehaviourNode> children;

    public Composite() {
        this.children = new ArrayList<>();
    }

    public void addChild(BehaviourNode child) {
        this.children.add(child);
    }

    @Override public EBehaviourStatus execute() {
        return null;
    }
}