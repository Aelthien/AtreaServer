package atrea.server.engine.networking.packet.listener;

import atrea.server.engine.networking.session.Session;
import io.netty.buffer.ByteBuf;

public class CraftItemPacketListener implements IPacketListener {

    @Override
    public void process(Session session, ByteBuf buffer) {

        int recipeId = buffer.readInt();

        
    }
}