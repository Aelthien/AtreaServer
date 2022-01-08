package atrea.server.networking.packet.listener;

import atrea.server.networking.session.PlayerSession;
import io.netty.buffer.ByteBuf;

public class CombineItemPacketListener implements IPacketListener {

    @Override
    public void processGamePacket(PlayerSession playerSession, ByteBuf buffer) {

        int item1 = buffer.readInt();
        int item2 = buffer.readInt();
    }
}