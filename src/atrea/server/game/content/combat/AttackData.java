package atrea.game.content.combat;

import atrea.game.entity.EDamageType;
import atrea.game.entity.EntityStatusEffect;
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