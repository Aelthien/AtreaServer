package atrea.server.game.entities.components.systems;

import atrea.server.game.entities.components.Entity;
import atrea.server.game.content.guilds.Guild;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.components.GuildComponent;

import java.util.ArrayList;
import java.util.List;

public class GuildSystem extends ComponentSystem<GuildComponent> {

    private List<Guild> guilds = new ArrayList<>();

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {

    }

    @Override public void update() {
    }
}