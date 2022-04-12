package atrea.server.game.entities.components;

import atrea.server.game.content.combat.AttackData;
import lombok.Getter;
import lombok.Setter;

import static atrea.server.game.entities.components.EComponentType.*;

public class CombatComponent extends EntityComponent {

    private @Getter @Setter Entity target;
    private @Getter boolean inCombat;
    private @Getter boolean retaliate;

    @Override public EComponentType getComponentType() {
        return COMBAT;
    }

    public CombatComponent(Entity parent) {
        super(parent);
    }

    @Override public void update() {

    }

    public void receiveAttack(AttackData attack) {

    }

    public CombatComponent setRetaliate(boolean retaliate) {
        this.retaliate = retaliate;
        return this;
    }
}