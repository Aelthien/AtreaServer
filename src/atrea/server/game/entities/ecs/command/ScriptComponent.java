package atrea.server.game.entities.ecs.command;

import atrea.server.game.entities.ecs.EComponentType;
import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.EntityComponent;
import lombok.Getter;
import lombok.Setter;

import static atrea.server.game.entities.ecs.EComponentType.COMMAND;

public class ScriptComponent extends EntityComponent {

    private @Getter @Setter Script script;

    public ScriptComponent(Entity parent) {
        super(parent);
    }

    @Override public void reset() {

    }

    @Override public EComponentType getComponentType() {
        return COMMAND;
    }

}