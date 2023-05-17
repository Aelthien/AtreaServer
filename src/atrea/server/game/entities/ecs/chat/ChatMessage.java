package atrea.server.game.entities.ecs.chat;

import atrea.server.game.entities.ecs.EGameRole;
import lombok.Getter;

public class ChatMessage {
    private @Getter
    EGameRole rank;
    private @Getter String clanTag;
    private @Getter String name;
    private @Getter String message;

    public ChatMessage(EGameRole rank, String clanTag, String name, String message) {
        this.rank = rank;
        this.clanTag = clanTag;
        this.name = name;
        this.message = message;
    }
}