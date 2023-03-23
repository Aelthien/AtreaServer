package atrea.server.engine.networking.packet.listener;

import atrea.server.engine.networking.session.Session;
import io.netty.buffer.ByteBuf;

public class PingPacketListener implements IPacketListener {

    @Override
    public void process(Session session, ByteBuf buffer) {
    }
}