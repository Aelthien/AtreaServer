package atrea.server.game.entities.ecs.ai.behaviourtree;

import atrea.server.game.entities.ecs.ai.behaviourtree.composites.Composite;
import lombok.Getter;

public class BehaviourTree {
    protected @Getter Blackboard blackboard;
    protected Composite root;

    public BehaviourTree(Blackboard blackboard) {
        this.blackboard = blackboard;
        this.root = new Composite();
    }

    public void update() {
        root.execute();
    }

    public static void traverse(BehaviourNode node, BehaviourNodeVisitor visitor) {
        visitor.visit(node);

        if (node instanceof Composite) {
            Composite composite = (Composite) node;

            for (BehaviourNode child : composite.getChildren()) {
                traverse(child, visitor);
            }
        }
    }

    interface BehaviourNodeVisitor {
        void visit(BehaviourNode node);
    }
}