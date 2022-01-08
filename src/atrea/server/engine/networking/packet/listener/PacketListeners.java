package atrea.server.networking.packet.listener;

import atrea.server.networking.packet.IncomingPacketConstants;

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