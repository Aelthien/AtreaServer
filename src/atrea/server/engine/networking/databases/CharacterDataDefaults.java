package atrea.server.engine.networking.databases;

import atrea.server.engine.accounts.CharacterWorldData;
import atrea.server.engine.utilities.Position;

public class CharacterDataDefaults {
    private static CharacterWorldData WORLD = new CharacterWorldData(new Position(0, 0, 0), false);

    public static CharacterWorldData getWorld() {
        return new CharacterWorldData(WORLD.getPosition().clone(), WORLD.isRunning());
    }
}
