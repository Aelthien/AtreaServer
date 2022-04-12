package atrea.server.game.entities;

import lombok.Getter;

import static atrea.server.game.entities.EDamageType.*;
import static atrea.server.game.entities.EEffectTarget.*;

public enum EEffect {
    POISONED(null, HEALTH, POISON,true),
    POISON_IMMUNE(new EEffect[]{POISONED},null,null,true),
    COMBAT_LOCK(null,null,null,true),
    ENVENOMED(new EEffect[]{POISONED},HEALTH, VENOM, true),
    VENOM_IMMUNE(new EEffect[]{ENVENOMED},HEALTH,VENOM,true),
    FROZEN(null,RUN,null,true),
    BLINDED(null,ACCURACY,null,true),
    BLEEDING(null,HEALTH, BLEED,true),
    COMBAT_BOOST( null,COMBAT_SKILLS,null,true),
    HUNGER_BASE_DEGEN(null, HUNGER,null,false),
    THIRST_BASE_DEGEN(null, THIRST,null, false),
    FAVOUR_BASE_REGEN(null, FAVOUR,null,false),
    HEALTH_REGEN(null, HEALTH,null,false);

    EEffect(EEffect[] overrides, EEffectTarget target, EDamageType damageType, boolean sentToClient) {
        this.overrides = overrides;
        this.target = target;
        this.damageType = damageType;
        this.updateClient = sentToClient;
    }

    /**
     * List of entity effects that this effect overrides.
     */
    private @Getter EEffect[] overrides;

    /**
     * The attribute that this effect targets. e.g. Health, energy or damage.
     */
    private @Getter EEffectTarget target;

    /**
     * The type of damage applied by this effect if applicable.
     */
    private @Getter EDamageType damageType;

    /**
     * Set whether this effect should be updated on the client when applied.
     */
    private @Getter boolean updateClient;
}