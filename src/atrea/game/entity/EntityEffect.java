package atrea.game.entity;

public class EntityEffect
{
	private EEffect effect;
	private int tickRate;
	private int currentTime;
	private int tickProgression;
	private int duration;
	private int amount;
	private boolean delete;

	public EntityEffect(EEffect effect, int duration, int tickRate, int amount)
	{
		this.effect = effect;
		this.duration = duration;
		this.tickRate = tickRate;
		this.amount = amount;
	}

	public EEffect getEffect() {
		return effect;
	}

	public EntityEffect setEffect(EEffect effect) {
		this.effect = effect;
		return this;
	}

	public int getCurrentTime() {
		return currentTime;
	}

	public EntityEffect setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
		return this;
	}

	public int getTickProgression() {
		return tickProgression;
	}

	public EntityEffect setTickProgression(int tickProgression) {
		this.tickProgression = tickProgression;
		return this;
	}

	public void decrementTime()
	{
		currentTime -= 1;
	}

	public void incrementTick()
	{
		tickProgression += 1;

		if (tickProgression == tickRate)
			resetTickProgression();
	}
	
	public void resetTickProgression()
	{
		tickProgression = 0;
	}

	public int getAmount() {
		return amount;
	}

	public EntityEffect setAmount(int amount) {
		this.amount = amount;
		return this;
	}

	public int getDuration() {
		return duration;
	}

	public EntityEffect setDuration(int duration) {
		this.duration = duration;
		return this;
	}

	public int getTickRate() {
		return tickRate;
	}

	public EntityEffect setDelete(boolean delete) {
		this.delete = delete;
		return this;
	}

	public boolean isDelete() {
		return delete;
	}
}