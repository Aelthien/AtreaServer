package atrea.server.game.entities.ecs.guild;

import atrea.server.game.content.guilds.EGuildRank;
import atrea.server.game.entities.ecs.EComponentType;
import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.EntityComponent;
import lombok.Getter;
import lombok.Setter;

import static atrea.server.game.entities.ecs.EComponentType.*;

public class GuildComponent extends EntityComponent {

    private @Getter @Setter int guildId;
    private @Getter @Setter EGuildRank rank;

    @Override public EComponentType getComponentType() {
        return GUILD;
    }

    public GuildComponent(Entity parent) {
        super(parent);
    }

    @Override public void reset() {

    }
}