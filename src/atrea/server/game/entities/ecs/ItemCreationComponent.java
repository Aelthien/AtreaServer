package atrea.server.game.entities.ecs;

import atrea.server.game.entities.Entity;
import lombok.Getter;
import lombok.Setter;

import static atrea.server.game.entities.ecs.EComponentType.*;

public class ItemCreationComponent extends EntityComponent {

    private @Getter @Setter int recipeId;

    @Override public EComponentType getComponentType() {
        return ITEM_CREATION;
    }

    public ItemCreationComponent(Entity parent) {
        super(parent);
    }

    @Override public void reset() {

    }
}