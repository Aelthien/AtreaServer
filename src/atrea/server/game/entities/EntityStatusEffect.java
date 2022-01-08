package atrea.server.game.entity;

import lombok.Getter;
import lombok.Setter;

public class EntityStatusEffect {

	private @Getter EEffect effect;
	private @Getter int value;
	private @Getter EValueType valueType;
	private @Getter @Setter int delay;
	private @Getter @Setter int duration;
	private @Getter @Setter int timeRemaining;
	private @Getter int activationChance;

	public EntityStatusEffect(EEffect effect, int value, EValueType valueType, int delay, int duration, int timeRemaining, int activationChance) {
		this.effect = effect;
		this.value = value;
		this.valueType = valueType;
		this.delay = delay;
		this.duration = duration;
		this.timeRemaining = timeRemaining;
		this.activationChance = activationChance;
	}

	public EntityStatusEffect(EEffect effect, int value, EValueType valueType, int duration, int delay) {
		this.value = value;
		this.valueType = valueType;
		this.effect = effect;
		this.duration = duration;
		this.delay = delay;
		this.activationChance = 100;
	}
}