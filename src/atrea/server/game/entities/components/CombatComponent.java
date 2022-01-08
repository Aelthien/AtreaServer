package atrea.server.game.entity.components;

import atrea.server.game.content.combat.AttackData;
import atrea.server.game.entity.Entity;
import lombok.Getter;

public class CombatComponent extends EntityComponent {

    private Entity target;
    private @Getter boolean inCombat;

    public CombatComponent(Entity parent) {
        super(parent);
    }

    public void receiveAttack(AttackData attack) {

    }
}