package atrea.server.game.entities.ecs.combat;

import atrea.server.game.content.combat.AttackData;
import atrea.server.game.entities.ecs.EComponentType;
import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.EntityComponent;
import lombok.Getter;
import lombok.Setter;

import static atrea.server.game.entities.ecs.EComponentType.*;

public class CombatComponent extends EntityComponent {

    private @Getter @Setter
    Entity target;
    private @Getter boolean inCombat;
    private @Getter boolean retaliate;

    @Override public EComponentType getComponentType() {
        return COMBAT;
    }

    public CombatComponent(Entity parent) {
        super(parent);
    }

    @Override public void reset() {

    }

    public void receiveAttack(AttackData attack) {

    }

    public CombatComponent setRetaliate(boolean retaliate) {
        this.retaliate = retaliate;
        return this;
    }
}