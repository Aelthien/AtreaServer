package atrea.server.game.entities.ecs.systems;

import atrea.server.game.entities.ecs.Entity;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.ChatComponent;
import atrea.server.engine.main.GameManager;
import atrea.server.engine.networking.packet.MessageSender;

import java.util.HashMap;
import java.util.Map;

public class ChatSystem extends ComponentSystem {
    private Map<Integer, ChatComponent> components = new HashMap<>();

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        components.put(entity.getEntityId(), new ChatComponent(entity));
    }

    @Override public void update() {
        for (ChatComponent component : components.values()) {
            if (!component.hasMessages())
                continue;

            MessageSender messageSender = GameManager
                    .getSessionManager()
                    .getPlayerSession(component.getParent().getDefinitionId())
                    .getMessageSender();

            while (component.hasMessages()) {
                messageSender.sendChatMessage(component.getNextMessage());
            }
        }
    }
}