package atrea.server.game.entities.ecs;

import atrea.server.game.content.guilds.EGuildRank;
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
}