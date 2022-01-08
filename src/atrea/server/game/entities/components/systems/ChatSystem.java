package atrea.server.game.entity.components.systems;

import atrea.server.game.entity.components.ChatComponent;
import atrea.server.game.world.GameManager;
import atrea.server.engine.networking.packet.MessageSender;

import java.util.HashMap;
import java.util.Map;

public class ChatSystem extends ComponentSystem {

    private Map<Integer, ChatComponent> components = new HashMap<>();

    @Override public void update() {
        for (ChatComponent component : components.values()) {
            if (!component.hasMessages())
                continue;

            MessageSender messageSender = GameManager
                    .getPlayerSessionManager()
                    .getPlayerSession(component.getParent())
                    .getMessageSender();

            while (component.hasMessages()) {
                messageSender.sendChatMessage(component.getNextMessage());
            }
        }
    }
}