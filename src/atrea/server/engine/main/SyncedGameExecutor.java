package atrea.server.game;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.ThreadPoolExecutor.*;

public class SyncedGameExecutor {
    private ExecutorService service;
    private Phaser phaser;

    private boolean CONCURRENCY = Runtime.getRuntime().availableProcessors() > 1;

    public SyncedGameExecutor() {
        service = CONCURRENCY ? create(Runtime.getRuntime().availableProcessors()) : null;
        phaser = CONCURRENCY ? new Phaser(1) : null;
    }

    private ExecutorService create(int nThreads) {
        if (nThreads <= 1)
            return null;

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads);
        executor.setRejectedExecutionHandler(new CallerRunsPolicy());
        executor.setThreadFactory(new ThreadFactoryBuilder().setNameFormat("SynchronisedGameThread").build());

        return Executors.unconfigurableExecutorService(executor);
    }

    public void sync(SyncedGameTask syncTask) {
        if (service == null || phaser == null || !syncTask.isConcurrent()) {
            for (int index = 1; index < syncTask.getCapacity(); index++) {
                if (!syncTask.checkIndex(index))
                    continue;
                syncTask.execute(index);
            }
            return;
        }

        phaser.bulkRegister(syncTask.getAmount());
        for (int index = 1; index < syncTask.getCapacity(); index++) {
            if (!syncTask.checkIndex(index))
                continue;
            final int finalIndex = index;
            service.execute(() -> {
                try {
                    syncTask.execute(finalIndex);
                } finally {
                    phaser.arriveAndDeregister();
                }
            });
        }
        phaser.arriveAndAwaitAdvance();
    }
}