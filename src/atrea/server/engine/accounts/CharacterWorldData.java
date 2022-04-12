package atrea.server.engine.accounts;

import atrea.server.engine.utilities.Position;
import lombok.Getter;
import lombok.Setter;

public class CharacterWorldData {
    private @Getter @Setter Position position;
    private @Getter @Setter boolean running;

    public CharacterWorldData(Position position, boolean running) {
        this.position = position;
        this.running = running;
    }
}