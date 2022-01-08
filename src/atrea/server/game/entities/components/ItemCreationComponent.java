package atrea.server.game.entity.components;

import atrea.server.game.entity.Entity;
import lombok.Getter;
import lombok.Setter;

public class ItemCreationComponent extends EntityComponent {

    private @Getter @Setter int recipeId;

    public ItemCreationComponent(Entity parent) {
        super(parent);
    }
}