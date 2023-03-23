package atrea.server.engine.networking.packet.listener;

import atrea.server.engine.main.GameManager;
import atrea.server.game.content.interactions.EInteractionOption;
import atrea.server.engine.networking.session.Session;
import atrea.server.game.entities.ecs.systems.InteractionSystem;
import io.netty.buffer.ByteBuf;

public class EntityActionPacketListener implements IPacketListener {

    @Override
    public void process(Session session, ByteBuf buffer) {
        int entityId = buffer.readInt();
        EInteractionOption option = EInteractionOption.values()[buffer.readByte()];

        InteractionSystem interactionSystem = GameManager.getSystemManager().getInteractionSystem();

        interactionSystem.getComponent(entityId).interact(session.getAccount().getCurrentCharacter().getEntityId(), option);
    }
}
