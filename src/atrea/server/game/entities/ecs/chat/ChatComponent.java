package atrea.server.game.entities.ecs.chat;

import atrea.server.game.entities.ecs.EComponentType;
import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.EntityComponent;

import java.util.ArrayDeque;
import java.util.Deque;

import static atrea.server.game.entities.ecs.EComponentType.*;

public class ChatComponent extends EntityComponent {

    private Deque<ChatMessage> messages = new ArrayDeque<>();

    @Override public EComponentType getComponentType() {
        return CHAT;
    }

    public ChatComponent(Entity parent) {
        super(parent);
    }

    @Override public void reset() {

    }

    public void addMessage(ChatMessage message) {
        messages.addFirst(message);
    }

    public boolean hasMessages() {
        return !messages.isEmpty();
    }

    public ChatMessage getNextMessage() {
        return messages.pop();
    }
}