package atrea.server.game.entities.components;

import atrea.server.game.content.interactions.EInteractionOption;
import atrea.server.game.data.definition.DefinitionManager;
import atrea.server.game.data.definition.EntityDefinition;
import atrea.server.game.entities.EntityStateInteractions;

import java.util.Arrays;

import static atrea.server.game.entities.components.EComponentType.INTERACTION;

public class InteractionComponent extends EntityComponent {

    public InteractionComponent(Entity parent) {
        super(parent);
    }

    @Override public EComponentType getComponentType() {
        return INTERACTION;
    }

    @Override public void update() {

    }

    public boolean interact(Entity interactingEntity, EInteractionOption option) {
        EntityDefinition definition = DefinitionManager.getEntityDefinition(parent.getDefinitionId());

        return false;
    }
}