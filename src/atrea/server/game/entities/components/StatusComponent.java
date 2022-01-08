package atrea.server.game.entity.components;

import atrea.server.game.entity.EEffectTarget;
import atrea.server.game.entity.EValueType;
import atrea.server.game.entity.Entity;
import atrea.server.game.entity.EntityStatusEffect;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class StatusComponent extends EntityComponent {
    private @Getter int health = 100;
    private @Getter int energy = 100;

    private @Getter List<EntityStatusEffect> effects = new ArrayList<>();

    public StatusComponent(Entity parent) {
        super(parent);
    }

    public void applyEffect(EntityStatusEffect effect) {
        EEffectTarget target = effect.getEffect().getTarget();
        EValueType valueType = effect.getValueType();
        int value = effect.getValue();

        switch (target) {
            case HEALTH:
                health = calculateFinalValue(health, value, valueType);
                break;
            default:
                break;
        }
    }

    private int calculateFinalValue(int effectedValue, int effectValue, EValueType valueType) {
        int newValue = 0;
        boolean positive = effectValue < 0;

        switch (valueType) {
            case PERCENT:
                float percentage = effectValue / 100;
                newValue = (int) (positive ? effectedValue + (effectedValue * percentage) : effectedValue - (effectedValue * percentage));
                break;
            case ABSOLUTE:
                newValue = effectValue;
                break;
            case MULTIPLY:
                newValue = effectedValue * effectValue;
                break;
            case RELATIVE:
                newValue = positive ? effectedValue + effectValue : effectedValue - effectValue;
                break;
            default:
                break;
        }

        return newValue;
    }
}