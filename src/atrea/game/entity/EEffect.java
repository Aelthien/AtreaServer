package atrea.game.entity;

import java.util.Arrays;
import java.util.List;

import static atrea.game.entity.EEffectTarget.*;

public enum EEffect
{
    POISON(null, HEALTH, true),
    ANTIPOISON(new EEffect[]{POISON},null,true),
    COMBAT_LOCK(null,null,true),
    VENOM(new EEffect[]{POISON},null,true),
    ANTIVENOM(new EEffect[]{VENOM},null,true),
    FREEZE(null,RUN,true),
    BLIND(null,ACCURACY,true),
    BLEED(null,null,true),
    COMBAT_BOOST( null,COMBAT_SKILLS,true),
    HUNGER_BASE_DEGEN(null, HUNGER,false),
    THIRST_BASE_DEGEN(null, THIRST, false),
    FAVOUR_BASE_REGEN(null, FAVOUR,false),
    HEALTH_REGEN(null, HEALTH,false);

    EEffect(EEffect[] overrides, EEffectTarget target, boolean sentToClient)
    {
        this.overrides = overrides;
        this.target = target;
        this.sentToClient = sentToClient;
    }

    private EEffect[] overrides;
    private EEffectTarget target;
    private boolean sentToClient;

    public List<EEffect> getOverrides() {
        return Arrays.asList(overrides);
    }

    public boolean isSentToClient() {
        return sentToClient;
    }
}
