package atrea.packet.listener;

import atrea.game.entity.Player;
import atrea.server.PlayerSession;
import io.netty.buffer.ByteBuf;

public interface IPacketListener
{
    void processGamePacket(PlayerSession playerSession, ByteBuf buffer);
}
