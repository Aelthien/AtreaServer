package atrea.server.engine.accounts;

import atrea.server.engine.utilities.Position;
import lombok.Getter;

public class PlayerWorldData {

    private @Getter Position position;

    public PlayerWorldData(Position position) {
        this.position = position;
    }
}