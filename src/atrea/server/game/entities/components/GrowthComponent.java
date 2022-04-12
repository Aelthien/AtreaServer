package atrea.server.game.entities.components;

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

    @Override public void update() {
        if (grown)
            return;
        else {
            if (growthStage == totalGrowthStages) {
                grown = true;
                return;
            } else {
                if (growthStageProgress == growthStageLength)
                    growthStage++;
            }
        }

        growthStageProgress++;
    }

    @Override public EComponentType getComponentType() {
        return null;
    }
}
