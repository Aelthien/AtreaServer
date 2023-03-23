package atrea.server.game.entities.ecs;

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