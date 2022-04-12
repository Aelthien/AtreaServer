package atrea.server.engine.networking.session;

import atrea.server.engine.accounts.Account;
import io.netty.util.AttributeKey;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private final AttributeKey<Session> SESSION_KEY = AttributeKey.valueOf("session.key");;
    private final Map<Integer, Session> playerSessions = new HashMap<>();

    public void update() {
        playerSessions.values().forEach(playerSession -> playerSession.update());
    }

    public AttributeKey<Session> getSessionKey() {
        return SESSION_KEY;
    }

    public void registerSession(Account account, Session session) {
        playerSessions.put(account.getId(), session);
    }

    public Session getPlayerSession(int playerId) {
        return playerSessions.get(playerId);
    }
}