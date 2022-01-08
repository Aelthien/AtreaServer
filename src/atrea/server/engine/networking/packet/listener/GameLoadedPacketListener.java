package atrea.server.networking.packet.listener;

import atrea.server.networking.session.PlayerSession;
import io.netty.buffer.ByteBuf;

public class GameLoadedPacketListener implements IPacketListener
{
    @Override
    public void processGamePacket(PlayerSession playerSession, ByteBuf buffer) {
        System.out.println("Client has loaded the game");
        playerSession.getDatabaseManager().load(playerSession.getPlayer());
        //GameManager.addNpc(new Entity());
    }
}