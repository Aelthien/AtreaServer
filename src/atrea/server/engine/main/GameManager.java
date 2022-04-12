package atrea.server.engine.main;

import atrea.server.game.entities.components.Entity;
import atrea.server.game.entities.components.EntityManager;
import atrea.server.game.entities.components.systems.SystemManager;
import atrea.server.engine.networking.session.Session;
import atrea.server.engine.networking.session.SessionManager;
import lombok.Getter;

public class GameManager {

    private static @Getter SessionManager sessionManager;
    private static @Getter EntityManager entityManager;
    private static @Getter SystemManager systemManager;

    static {
        sessionManager = new SessionManager();
        systemManager = new SystemManager();
        entityManager = new EntityManager(systemManager);
    }

    public static void update() {
        sessionManager.update();
        systemManager.update();
        entityManager.update();
    }

    public static void addPlayer(Session session) {
        Entity player = entityManager.createPlayer();
        session.getAccount().setCurrentCharacter(player);
        entityManager.queueEntityAdd(player);
    }

    /*public static void update() {
        for (int i = 0; i < QUEUE_LIMIT; i++) {
            Player player = addPlayerQueue.poll();

            if (player == null)
                break;

            players.add(player);
        }

        int amount = 0;
        Iterator<Player> playerIterator = removePlayerQueue.iterator();
        while (playerIterator.hasNext()) {
            Player player = playerIterator.next();

            if (player == null || amount >= QUEUE_LIMIT) {
                break;
            }

            if (false) {
                players.remove(player);
                playerIterator.remove();
            }

            amount++;
        }

        for (int i = 0; i < QUEUE_LIMIT; i++) {
            Entity npc = addNpcQueue.poll();

            if (npc == null)
                break;

            npcs.add(npc);
        }

        executor.sync(new SyncedGameTask(EEntityType.Player, false) {
            @Override public void execute(int index) {
                Player player = players.get(index);

                try {
                    player.sequence();
                } catch (Exception e) {
                    e.printStackTrace();
                    player.getPlayerSession().setSessionState(ESessionState.LOGGING_OUT);
                }
            }
        });
    }*/
}
