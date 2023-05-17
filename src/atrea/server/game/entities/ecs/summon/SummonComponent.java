package atrea.server.game.entities.ecs.summon;

import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.EComponentType;
import atrea.server.game.entities.ecs.EntityComponent;

public class SummonComponent extends EntityComponent {

    public SummonComponent(ComponentDefinition definition, Entity parent) {
        super(parent);
    }

    @Override public EComponentType getComponentType() {
        return EComponentType.SUMMON;
    }

    @Override public void reset() {

    }
}