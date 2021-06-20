package atrea.packet.listener;

import atrea.packet.listener.impl.GameLoadedPacketListener;
import atrea.packet.listener.impl.PingPacketListener;
import atrea.packet.listener.impl.TileClickListener;
import atrea.server.IncomingPacketConstants;

public class PacketListeners extends IncomingPacketConstants
{
    private static IPacketListener[] LISTENERS;

    static
    {
        LISTENERS = new IPacketListener[256];

        LISTENERS[GAME_LOADED] = new GameLoadedPacketListener();
        LISTENERS[TILE_CLICK] = new TileClickListener();
    }

    public static IPacketListener getListener(int opcode)
    {
        return LISTENERS[opcode];
    }
}