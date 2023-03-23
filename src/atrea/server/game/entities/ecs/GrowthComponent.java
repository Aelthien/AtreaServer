package atrea.server.game.entities.ecs;

import lombok.Getter;

public class GrowthComponent extends EntityComponent {

    private @Getter int growthStage;
    private @Getter int growthStageProgress;
    private @Getter int growthStageLength;
    private @Getter int totalGrowthStages;
    private @Getter boolean grown;

    public GrowthComponent(Entity parent) {
        super(parent);
    }

    @Override public EComponentType getComponentType() {
        return null;
    }
}
