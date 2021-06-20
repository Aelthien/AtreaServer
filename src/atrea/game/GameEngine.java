package atrea.game;

import atrea.game.world.RegionManager;
import atrea.game.world.World;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameEngine implements Runnable
{
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("GameEngine").build());

    private final int GAME_TICK = 250;

    public void init() {
        RegionManager.initialise();
        System.out.println("Initializing game engine.");
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
    public void run()
    {
        World.update();
    }
}