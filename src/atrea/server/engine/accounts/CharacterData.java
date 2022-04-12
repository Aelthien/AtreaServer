package atrea.server.engine.accounts;

import atrea.server.engine.networking.databases.EGender;
import atrea.server.engine.utilities.Position;
import lombok.Getter;
import lombok.Setter;

public class CharacterData {
    private @Getter @Setter int id;
    private @Getter @Setter CharacterGeneralData generalData;
    private @Getter @Setter CharacterWorldData worldData;

    public CharacterData(int id, CharacterGeneralData generalData, CharacterWorldData worldData) {
        this.id = id;
        this.generalData = generalData;
        this.worldData = worldData;
    }

    public CharacterData(CharacterGeneralData generalData, CharacterWorldData worldData) {
        this.generalData = generalData;
        this.worldData = worldData;
    }

    public static CharacterData create(String name, EGender gender) {
        return new CharacterData(
                new CharacterGeneralData(name, gender, 1, -1, ELocation.EMERALD_ISLE),
                new CharacterWorldData(new Position(), false));
    }
}