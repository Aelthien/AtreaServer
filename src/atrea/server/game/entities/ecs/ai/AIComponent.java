package atrea.server.game.entities.ecs.ai;

import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.EComponentType;
import atrea.server.game.entities.ecs.EntityComponent;
import atrea.server.game.entities.ecs.ai.behaviourtree.BehaviourTree;
import atrea.server.game.entities.ecs.ai.behaviourtree.Blackboard;
import atrea.server.game.entities.ecs.ai.behaviourtree.MoveToPlayer;
import lombok.Getter;
import lombok.Setter;

import static atrea.server.game.entities.ecs.EComponentType.*;

public class AIComponent extends EntityComponent {

    private @Getter @Setter BehaviourTree behaviourTree;

    @Override public EComponentType getComponentType() {
        return AI;
    }

    public AIComponent(ComponentDefinition definition, Entity parent) {
        super(parent);

        switch (definition.getValues()[0]) {
            case "MoveToPlayer":
                behaviourTree = new MoveToPlayer(new Blackboard());
            default:
                break;
        }
    }

    @Override public void reset() {

    }
}
