package atrea.server.networking.packet.listener;

import atrea.server.networking.session.PlayerSession;
import io.netty.buffer.ByteBuf;

public interface IPacketListener
{
    void processGamePacket(PlayerSession playerSession, ByteBuf buffer);
}
