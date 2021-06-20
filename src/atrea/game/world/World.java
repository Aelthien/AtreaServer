package atrea.game.world;

import atrea.game.SyncedGameExecutor;
import atrea.game.SyncedGameTask;
import atrea.game.content.items.Item;
import atrea.game.entity.*;
import atrea.game.entity.components.impl.NetworkComponent;
import atrea.main.Position;
import atrea.server.ESessionState;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class World {
    private static Queue<Player> addPlayerQueue = new ConcurrentLinkedQueue<>();
    private static Queue<Player> removePlayerQueue = new ConcurrentLinkedQueue<>();
    private static Queue<Entity> addNpcQueue = new ConcurrentLinkedQueue<>();
    private static Queue<Entity> removeNpcQueue = new ConcurrentLinkedQueue<>();
    private static @Getter EntityList<Player> players = new EntityList<>(10240);
    private static @Getter EntityList<Entity> npcs = new EntityList<>(5120);
    private static @Getter List<Entity> entities = new ArrayList<>();

    private static SyncedGameExecutor executor = new SyncedGameExecutor();

    private static final int QUEUE_LIMIT = 50;
    private static EntityBuilder entityBuilder = new EntityBuilder();

    public static void update() {
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
    }

    public static List<Entity> getLocalPlayers(Position position) {
        List<Entity> players = null;

        return players;
    }

    public static List<Entity> getLocalEntities(Position position) {
        List<Entity> localEntities = null;

        for (Entity entity : entities) {
            if (entity.getTransform().getPosition().isWithinRange(position, 64))
                localEntities.add(entity);
        }

        return localEntities;
    }

    public static Player addPlayer() {
        Player player = entityBuilder.buildPlayer();

        addPlayerQueue.add(player);

        return player;
    }

    public static Entity addNpc(Entity npc) {
        addNpcQueue.add(npc);

        return npc;
    }

    public static void shutdown() {
        for (Entity player : players) {
            NetworkComponent networkComponent = player.getComponent(NetworkComponent.class);
        }
    }

    public static Queue<Player> getAddPlayerQueue() {
        return addPlayerQueue;
    }

    public static void spawnItem(Item item, int x, int y) {

    }
}
