package atrea.server.game.world;

import atrea.server.game.entity.*;
import atrea.server.engine.networking.session.PlayerSessionManager;
import lombok.Getter;

public class GameManager {

    private static @Getter PlayerSessionManager playerSessionManager;
    private static @Getter EntityManager entityManager;

    private static final int QUEUE_LIMIT = 50;

    static {
        playerSessionManager = new PlayerSessionManager();
        entityManager = new EntityManager();
    }

    public static void update() {
        playerSessionManager.update();
        entityManager.update();
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
