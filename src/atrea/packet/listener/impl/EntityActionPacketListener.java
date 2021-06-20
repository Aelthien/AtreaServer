package atrea.packet.listener.impl;

import atrea.game.content.interactions.EInteractionOption;
import atrea.game.entity.EEntityType;
import atrea.game.entity.Player;
import atrea.packet.impl.EntityActionPacket;
import atrea.packet.listener.IPacketListener;
import atrea.server.PlayerSession;
import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

public class EntityActionPacketListener implements IPacketListener {

    @Override
    public void processGamePacket(PlayerSession playerSession, ByteBuf buffer) {

        EEntityType type = EEntityType.values()[buffer.readUnsignedShort()];
        EInteractionOption option = EInteractionOption.values()[buffer.readUnsignedByte()];
    }
}