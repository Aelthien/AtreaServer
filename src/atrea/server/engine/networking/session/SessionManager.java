package atrea.server.engine.networking.session;

import atrea.server.engine.entities.Entity;
import io.netty.util.AttributeKey;

import java.util.HashMap;
import java.util.Map;

public class PlayerSessionManager {
    private final AttributeKey<PlayerSession> SESSION_KEY = AttributeKey.valueOf("session.key");;
    private final Map<Entity, PlayerSession> playerSessions = new HashMap<>();

    public void update() {
        playerSessions.values().forEach(playerSession -> playerSession.update());
    }

    public AttributeKey<PlayerSession> getSessionKey() {
        return SESSION_KEY;
    }

    public void registerPlayerSession(Entity player, PlayerSession playerSession) {
        playerSessions.put(player, playerSession);
    }

    public PlayerSession getPlayerSession(Entity player) {
        return playerSessions.get(player);
    }
}
