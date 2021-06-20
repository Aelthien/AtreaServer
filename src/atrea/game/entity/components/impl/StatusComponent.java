package atrea.game.entity.components.impl;

import atrea.game.entity.Entity;
import atrea.game.entity.components.EntityComponent;

public class StatusComponent extends EntityComponent
{
    private int health = 100;

    public StatusComponent(Entity parent) {
        super(parent);
    }

    public int getHealth() {
        return health;
    }
}
