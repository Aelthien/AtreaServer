package atrea.game;

import atrea.server.Bootstrap;

public class Server
{
    public static void main(String[] args)
    {
        try {
            new GameEngine().init();
            new Bootstrap().bind();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        System.setProperty("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "WARNING");
        System.setProperty("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");

        configureShutdownProcedure();
    }

    private static void configureShutdownProcedure()
    {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run()
            {
            System.out.println("Shutdown");
            }
        }));
    }
}