package atrea.server.game.entity.components;

import atrea.server.game.content.guilds.EGuildRank;
import atrea.server.game.entity.Entity;
import lombok.Getter;
import lombok.Setter;

public class GuildComponent extends EntityComponent {

    private @Getter @Setter int guildId;
    private @Getter @Setter EGuildRank rank;

    public GuildComponent(Entity parent) {
        super(parent);
    }
}