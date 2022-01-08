package atrea.server.networking.packet.listener;

import atrea.server.networking.session.PlayerSession;
import io.netty.buffer.ByteBuf;

public class CraftItemPacketListener implements IPacketListener {

    @Override
    public void processGamePacket(PlayerSession playerSession, ByteBuf buffer) {

        int recipeId = buffer.readInt();

        
    }
}