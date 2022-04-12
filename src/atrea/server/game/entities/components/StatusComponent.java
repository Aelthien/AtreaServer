package atrea.server.game.entities.components;

import atrea.server.game.entities.EEffectTarget;
import atrea.server.game.entities.EValueType;
import atrea.server.game.entities.EntityStatusEffect;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static atrea.server.game.entities.components.EComponentType.*;

public class StatusComponent extends EntityComponent {

    private @Getter int health = 100;
    private @Getter int energy = 100;

    private @Getter List<EntityStatusEffect> effects = new ArrayList<>();
    private List<EntityStatusEffect> effectsToRemove = new ArrayList<>();

    @Override public EComponentType getComponentType() {
        return STATUS;
    }

    public StatusComponent(Entity parent) {
        super(parent);
    }

    @Override public void update() {

    }

    public void tick() {
        effects.removeAll(effectsToRemove);

        for (var effect : effects) {
            if (effect.isCompleted()) {
                effectsToRemove.add(effect);
                continue;
            }

            if (effect.activate()) {
                applyEffect(effect);
                effect.reset();
            }

            effect.tick();
        }
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
        int newValue = effectedValue;
        boolean positive = effectValue > 0;

        switch (valueType) {
            case PERCENT:
                float percentage = effectValue / 100;
                newValue = (int) (effectedValue + (effectedValue * percentage));
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

    public List<EntityStatusEffect> getEffectsNeedingUpdate() {
        return effects.stream().filter(effect -> effect.getEffect().isUpdateClient()).collect(Collectors.toList());
    }

    public StatusComponent setHealth(int health) {
        this.health = health;
        return this;
    }

    public StatusComponent setEnergy(int energy) {
        this.energy = energy;
        return this;
    }
}