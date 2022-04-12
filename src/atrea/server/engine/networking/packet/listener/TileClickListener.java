package atrea.server.engine.networking.packet.listener;

import atrea.server.engine.main.GameManager;
import atrea.server.engine.utilities.Position;
import atrea.server.engine.networking.session.Session;
import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;

public class TileClickListener implements IPacketListener {
    @Override
    public void process(Session session, ByteBuf buffer) {
        int x = buffer.readInt();
        int y = buffer.readInt();

        System.out.println("Received: " + x + " " + y + " tile");

        int entityId = session.getAccount().getCurrentCharacter().getEntityId();

        GameManager.getSystemManager()
                .getMovementSystem()
                .moveEntity(entityId, new Position(x, y), true);

        ReferenceCountUtil.release(buffer);
    }
}