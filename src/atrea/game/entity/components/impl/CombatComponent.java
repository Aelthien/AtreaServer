package atrea.game.entity.components.impl;

import atrea.game.entity.AttackData;
import atrea.game.entity.Entity;
import atrea.game.entity.components.EntityComponent;

public class CombatComponent extends EntityComponent
{
    private Entity target;

    public CombatComponent(Entity parent) {
        super(parent);
    }

    public void initiateCombat(Entity newTarget)
    {
        if (target != null)
            return;

        if (!target.hasComponent("CombatComponent"))
            return;

        target = newTarget;
    }

    public void attack(AttackData data)
    {

    }
}