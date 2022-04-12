package atrea.server.engine.networking.packet.listener;

import atrea.server.engine.networking.session.Session;
import io.netty.buffer.ByteBuf;

public class CombineItemPacketListener implements IPacketListener {

    @Override
    public void process(Session session, ByteBuf buffer) {

        int item1 = buffer.readInt();
        int item2 = buffer.readInt();
    }
}