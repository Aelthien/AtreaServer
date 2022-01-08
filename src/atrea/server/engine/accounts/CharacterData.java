package atrea.server.engine.accounts;

import lombok.Getter;

public class PlayerData {
    private @Getter int id;
    private @Getter PlayerGeneralData generalData;
    private @Getter PlayerWorldData worldData;

    public PlayerData() {
        id = -1;
    }

    public PlayerData(int id, PlayerGeneralData generalData, PlayerWorldData worldData) {
        this.generalData = generalData;
        this.worldData = worldData;
    }
}
