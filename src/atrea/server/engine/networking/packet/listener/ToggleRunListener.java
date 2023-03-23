package atrea.server.engine.networking.packet.listener;

import atrea.server.engine.main.GameManager;
import atrea.server.engine.networking.session.Session;
import atrea.server.engine.utilities.Position;
import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;

public class ToggleRunListener implements IPacketListener {
    @Override
    public void process(Session session, ByteBuf buffer) {
        int entityId = session.getAccount().getCurrentCharacter().getEntityId();
        boolean running = buffer.readBoolean();

        GameManager.getSystemManager()
                .getMovementSystem()
                .toggleRun(entityId, running);

        ReferenceCountUtil.release(buffer);
    }
}