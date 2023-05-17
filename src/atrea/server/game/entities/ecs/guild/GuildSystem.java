package atrea.server.game.entities.ecs.guild;

import atrea.server.game.entities.Entity;
import atrea.server.game.content.guilds.Guild;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.systems.ComponentSystem;

import java.util.ArrayList;
import java.util.List;

public class GuildSystem extends ComponentSystem<GuildComponent> {

    private List<Guild> guilds = new ArrayList<>();

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {

    }

    @Override public void update() {
    }
}