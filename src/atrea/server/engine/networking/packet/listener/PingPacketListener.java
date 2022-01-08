package atrea.server.networking.packet.listener;

import atrea.server.networking.session.PlayerSession;
import io.netty.buffer.ByteBuf;

public class PingPacketListener implements IPacketListener
{
    @Override
    public void processGamePacket(PlayerSession playerSession, ByteBuf buffer)
    {
        System.out.println("Ping from client");
        playerSession.getMessageSender().sendPing();
    }
}