package atrea.server.engine.networking.packet.listener;

import atrea.server.engine.main.GameManager;
import atrea.server.game.content.interactions.EInteractionOption;
import atrea.server.game.content.items.ItemContainer.EContainerType;
import atrea.server.engine.networking.session.Session;
import atrea.server.game.content.items.ItemContainer;
import atrea.server.game.entities.EInteractableType;
import atrea.server.game.entities.ecs.systems.SystemManager;
import io.netty.buffer.ByteBuf;

public class InteractionPacketListener implements IPacketListener {

    @Override
    public void process(Session session, ByteBuf buffer) {
        int subjectId = buffer.readInt();
        EInteractionOption option = EInteractionOption.values()[buffer.readByte()];
        EInteractableType entityType = EInteractableType.values()[buffer.readByte()];
        ItemContainer.EContainerType containerType = EContainerType.values()[buffer.readByte()];

        SystemManager systemManager = GameManager.getSystemManager();

        int playerId = session.getAccount().getCurrentCharacter().getEntityId();

        if (entityType == EInteractableType.ITEM) {
            switch (containerType) {
                case INVENTORY:
                    systemManager.getInventorySystem().getComponent(playerId);

                    //GameManager.getItemManager().interact(playerId, subjectId, option, containerType);
                    break;
                case EQUIPMENT:
//                    interactionSystem.getComponent(subjectId).interact(session.getAccount().getCurrentCharacter().getEquipment().getEntityId(), option);
                    break;
                case BANK:
//                    interactionSystem.getComponent(subjectId).interact(session.getAccount().getCurrentCharacter().getBank().getEntityId(), option);
                    break;
            }
        } else {
//            interactionSystem.getComponent(subjectId).interact(session.getAccount().getCurrentCharacter().getEntityId(), option);
        }
    }
}
