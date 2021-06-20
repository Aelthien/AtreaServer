package atrea.packet.listener.impl;

import atrea.game.ai.pathfinding.PathfinderComponent;
import atrea.main.Position;
import atrea.packet.listener.IPacketListener;
import atrea.server.PlayerSession;
import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;

public class TileClickListener implements IPacketListener {

    @Override
    public void processGamePacket(PlayerSession playerSession, ByteBuf buffer) {
        int x = buffer.readInt();
        int y = buffer.readInt();

        System.out.println("Received: " + x + " " + y + " tile");

        PathfinderComponent pathfinder = playerSession.getPlayer().getComponent(PathfinderComponent.class);
        pathfinder.findPath(new Position(x, y));

        ReferenceCountUtil.release(buffer);
    }
}