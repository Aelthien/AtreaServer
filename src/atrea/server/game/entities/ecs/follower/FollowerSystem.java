package atrea.server.game.entities.ecs.follower;

import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.summon.SummonSystem;
import atrea.server.game.entities.ecs.systems.ComponentSystem;

public class FollowerSystem extends ComponentSystem<FollowerComponent> {

    private SummonSystem summonSystem;

    public FollowerSystem(SummonSystem summonSystem) {
        componentsArray = new FollowerComponent[5000];

        this.summonSystem = summonSystem;
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        componentsArray[entity.getEntityId()] = new FollowerComponent(entity);
    }

    @Override public void update() {

    }
}
