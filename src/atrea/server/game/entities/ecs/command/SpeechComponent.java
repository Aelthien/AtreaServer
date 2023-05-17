package atrea.server.game.entities.ecs.command;

import atrea.server.game.entities.ecs.EComponentType;
import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.EntityComponent;
import lombok.Getter;

import static atrea.server.game.entities.ecs.EComponentType.SPEECH;

public class SpeechComponent extends EntityComponent {

    private @Getter String message;
    private @Getter int duration;

    public SpeechComponent(Entity parent) {
        super(parent);
    }

    @Override public void reset() {

    }

    @Override public EComponentType getComponentType() {
        return SPEECH;
    }

    public void setMessage(int duration, String message) {
        this.duration = duration;
        this.message = message;
    }
    public void resetMessage() {
        message = "";
    }

    public boolean hasMessage() {
        return !message.isEmpty();
    }
}