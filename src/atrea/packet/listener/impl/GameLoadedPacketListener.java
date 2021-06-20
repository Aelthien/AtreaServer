package atrea.packet.listener.impl;

import atrea.game.entity.Entity;
import atrea.game.world.World;
import atrea.packet.listener.IPacketListener;
import atrea.server.PlayerSession;
import io.netty.buffer.ByteBuf;

public class GameLoadedPacketListener implements IPacketListener
{
    @Override
    public void processGamePacket(PlayerSession playerSession, ByteBuf buffer) {
        System.out.println("Client has loaded the game");
        playerSession.getDatabaseManager().load(playerSession.getPlayer());
        World.addNpc(new Entity());
    }
}