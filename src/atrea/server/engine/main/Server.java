package atrea.server.engine.main;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Server {
    public static void main(String[] args) {
        try {
            new GameEngine().initialize();
            new Bootstrap().bind();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.setProperty("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "WARNING");
        System.setProperty("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");

        configureShutdownProcedure();
    }

    private static void configureShutdownProcedure() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Shutdown");
            }
        }));
    }
}