package atrea.server.game.entities.ecs.systems;

import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.command.SpeechComponent;

public class SpeechSystem extends ComponentSystem<SpeechComponent> {

    public SpeechSystem() {
        componentsArray = new SpeechComponent[5000];
    }

    public void setSpeech(int entityId, int duration, String message) {
        componentsArray[entityId].setMessage(duration, message);
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        componentsArray[entity.getEntityId()] = new SpeechComponent(entity);
    }

    @Override public void update() {
    }
}