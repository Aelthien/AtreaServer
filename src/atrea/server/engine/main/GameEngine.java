package atrea.server.engine.main;

import atrea.server.game.data.definition.DefinitionManager;
import atrea.server.engine.world.RegionManager;
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

        executorService.scheduleAtFixedRate(this::run, 0, GAME_TICK, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        GameManager.update();
    }
}