package atrea.server.engine.networking.packet.listener;

import atrea.server.engine.networking.packet.incoming.IncomingPacketConstants;

public class PacketListeners extends IncomingPacketConstants {
    private static IPacketListener[] LISTENERS;

    static {
        LISTENERS = new IPacketListener[256];

        LISTENERS[GAME_LOADED] = new GameLoadedPacketListener();
        LISTENERS[CREATE_CHARACTER] = new CreateCharacterPacketListener();
        LISTENERS[TILE_CLICK] = new TileClickListener();
        LISTENERS[PLAY_CHARACTER] = new PlayCharacterPacketListener();
    }

    public static IPacketListener getListener(int opcode) {
        return LISTENERS[opcode];
    }
}