package atrea.server.game.entities.ecs.systems;

import atrea.server.engine.main.GameManager;
import atrea.server.engine.networking.session.SessionManager;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.Entity;
import atrea.server.game.entities.ecs.command.SpeechComponent;

public class SpeechSystem extends ComponentSystem<SpeechComponent> {

    public SpeechSystem() {
        components = new SpeechComponent[5000];
    }

    public void setSpeech(int entityId, int duration, String message) {
        components[entityId].setMessage(duration, message);
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        components[entity.getEntityId()] = new SpeechComponent(entity);
    }

    @Override public void update() {
    }
}