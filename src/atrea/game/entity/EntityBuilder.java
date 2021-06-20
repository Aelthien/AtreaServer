package atrea.game.entity;

import atrea.game.ai.pathfinding.PathfinderComponent;
import atrea.game.entity.components.impl.*;

public class EntityBuilder {

    public Player buildPlayer() {
        Player player = new Player();
        player.setEntityType(EEntityType.Player);

        player.addComponent(new StatusComponent(player));
        player.addComponent(new CombatComponent(player));
        player.addComponent(new SkillsComponent(player));
        player.addComponent(new InventoryComponent(player));
        player.addComponent(new NetworkComponent(player, new PlayerDatabaseProcedure(player)));
        player.addComponent(new MovementComponent(player));
        player.addComponent(new PathfinderComponent(player));

        return player;
    }
}