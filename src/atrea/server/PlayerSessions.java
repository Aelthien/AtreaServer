package atrea.server;

import atrea.game.entity.Entity;
import atrea.game.entity.Player;
import io.netty.util.AttributeKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerSessions
{
    private static final AttributeKey<PlayerSession> SESSION_KEY = AttributeKey.valueOf("session.key");;
    private static final Map<Player, PlayerSession> playerSessions = new HashMap<>();

    public static AttributeKey<PlayerSession> getSessionKey() {
        return SESSION_KEY;
    }

    public static void registerPlayerSession(Player player, PlayerSession playerSession) {
        player.setPlayerSession(playerSession);
        playerSessions.put(player, playerSession);
    }

    public static Map<Player, PlayerSession> getPlayerSessions() {
        return playerSessions;
    }

    public static PlayerSession getPlayerSession(Entity player)
    {
        return playerSessions.get(player);
    }
}
