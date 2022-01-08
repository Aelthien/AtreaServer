package atrea.game;

import atrea.game.data.definition.DefinitionManager;
import atrea.game.world.RegionManager;
import atrea.game.world.GameManager;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameEngine implements Runnable {

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("GameEngine").build());

    private static final int GAME_TICK = 250;

    public static int getTicksPerSecond() {
        return 1000 / GAME_TICK;
    }

    public void initialize() {
        System.out.println("Initializing game engine.");

        DefinitionManager.initialize();
        RegionManager.initialise();

        executorService.scheduleAtFixedRate(this, 0, GAME_TICK, TimeUnit.MILLISECONDS);

        /*Node node1 = new Node(new Tile(1, 1, 0, 0,true));
        Node node2 = new Node(new Tile(1, 1, 0, 0,true));
        //Node node2 = new Node(new Tile(2, 2, 0,0, true));

        Node node1copy = node1;

        List<Node> nodes = new ArrayList<>();
        nodes.add(node1);

        if (nodes.contains(node2))
            System.out.println("does");*/
    }

    @Override
    public void run() {
        GameManager.update();
    }
}