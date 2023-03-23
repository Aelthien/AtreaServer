package atrea.server.engine.main;

import atrea.server.game.data.definition.DefinitionManager;
import atrea.server.engine.world.RegionManager;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class GameEngine implements Runnable {

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("GameEngine").build());

    private static final int GAME_TICK = 250;
    private static @Getter int currentTick;
    private static @Getter long tickStartTime;

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
        tickStartTime = System.currentTimeMillis();

        GameManager.update();

        //System.out.println("Tick: " + currentTick);

        currentTick++;
    }
}