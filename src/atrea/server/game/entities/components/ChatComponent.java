package atrea.server.game.entity.components;

import atrea.server.game.entity.Entity;

import java.util.ArrayDeque;
import java.util.Deque;

public class ChatComponent extends EntityComponent {

    private Deque<ChatMessage> messages = new ArrayDeque<>();

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