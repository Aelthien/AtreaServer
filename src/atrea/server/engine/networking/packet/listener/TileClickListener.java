package atrea.server.networking.packet.listener;

import atrea.server.game.world.GameManager;
import atrea.server.utilities.Position;
import atrea.server.networking.session.PlayerSession;
import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;

public class TileClickListener implements IPacketListener {
    @Override
    public void processGamePacket(PlayerSession playerSession, ByteBuf buffer) {
        int x = buffer.readInt();
        int y = buffer.readInt();

        System.out.println("Received: " + x + " " + y + " tile");

        int entityId = playerSession.getPlayer().getEntityId();

        GameManager.getEntityManager()
                .getMovementSystem()
                .moveEntity(entityId, new Position(x, y), true);

        ReferenceCountUtil.release(buffer);
    }
}