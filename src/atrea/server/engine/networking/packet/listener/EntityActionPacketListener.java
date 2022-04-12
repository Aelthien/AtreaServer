package atrea.server.engine.networking.packet.listener;

import atrea.server.game.content.interactions.EInteractionOption;
import atrea.server.game.entities.EEntityType;
import atrea.server.engine.networking.session.Session;
import io.netty.buffer.ByteBuf;

public class EntityActionPacketListener implements IPacketListener {

    @Override
    public void process(Session session, ByteBuf buffer) {

        EEntityType type = EEntityType.values()[buffer.readUnsignedShort()];
        EInteractionOption option = EInteractionOption.values()[buffer.readUnsignedByte()];
    }
}