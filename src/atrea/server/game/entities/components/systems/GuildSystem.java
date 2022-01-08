package atrea.server.game.entity.components.systems;

import atrea.server.game.content.guilds.Guild;
import atrea.server.game.entity.components.GuildComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuildSystem extends ComponentSystem {
    private Map<Integer, GuildComponent> components = new HashMap<>();
    private List<Guild> guilds = new ArrayList<>();

    @Override public void update() {
    }
}