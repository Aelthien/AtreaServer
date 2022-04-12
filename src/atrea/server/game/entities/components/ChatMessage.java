package atrea.server.game.entities.components;

import lombok.Getter;

public class ChatMessage {
    private @Getter EPlayerRank rank;
    private @Getter String clanTag;
    private @Getter String name;
    private @Getter String message;

    public ChatMessage(EPlayerRank rank, String clanTag, String name, String message) {
        this.rank = rank;
        this.clanTag = clanTag;
        this.name = name;
        this.message = message;
    }
}