package atrea.packet.listener.impl;

import atrea.packet.listener.IPacketListener;
import atrea.server.PlayerSession;
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