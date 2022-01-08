package atrea.server.networking.packet.listener;

import atrea.server.game.content.interactions.EInteractionOption;
import atrea.server.game.entity.EEntityType;
import atrea.server.networking.session.PlayerSession;
import io.netty.buffer.ByteBuf;

public class EntityActionPacketListener implements IPacketListener {

    @Override
    public void processGamePacket(PlayerSession playerSession, ByteBuf buffer) {

        EEntityType type = EEntityType.values()[buffer.readUnsignedShort()];
        EInteractionOption option = EInteractionOption.values()[buffer.readUnsignedByte()];
    }
}