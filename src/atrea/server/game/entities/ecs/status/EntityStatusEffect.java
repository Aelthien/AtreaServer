package atrea.server.game.entities.ecs.status;

import atrea.server.game.entities.EEffect;
import atrea.server.game.entities.EValueType;
import lombok.Getter;
import lombok.Setter;

public class EntityStatusEffect {

	private @Getter
    EEffect effect;
	private @Getter int value;
	private @Getter
    EValueType valueType;
	private @Getter @Setter int delay;
	private @Getter @Setter int duration;
	private @Getter @Setter int timeRemaining;
	private @Getter int activationChance;
	private @Getter int currentDelayTick;

	public EntityStatusEffect(EEffect effect, int value, EValueType valueType, int delay, int duration, int activationChance) {
		this.effect = effect;
		this.value = value;
		this.valueType = valueType;
		this.delay = delay;
		this.duration = duration;
		this.activationChance = activationChance;
		this.timeRemaining = duration;
	}

	public EntityStatusEffect(EEffect effect, int value, EValueType valueType, int duration, int delay) {
		this.value = value;
		this.valueType = valueType;
		this.effect = effect;
		this.duration = duration;
		this.delay = delay;
		this.activationChance = 100;
		this.timeRemaining = duration;
	}

	public void tick() {
		timeRemaining--;
		currentDelayTick--;
	}

	public boolean isCompleted() {
		return timeRemaining == 0;
	}

	public boolean activate() {
		return currentDelayTick == 0;
	}

	public void reset() {
		currentDelayTick = delay;
	}
}