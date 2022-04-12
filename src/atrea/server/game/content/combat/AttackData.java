package atrea.server.game.content.combat;

import atrea.server.game.entities.EDamageType;
import atrea.server.game.entities.EntityStatusEffect;
import lombok.Getter;

public class AttackData {

    private @Getter int damage;
    private @Getter EDamageType damageType;
    private @Getter EntityStatusEffect[] effects;

    public AttackData(int damage, EDamageType damageType, EntityStatusEffect[] effects) {
        this.damage = damage;
        this.damageType = damageType;
        this.effects = effects;
    }
}