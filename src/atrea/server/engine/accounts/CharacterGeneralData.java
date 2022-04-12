package atrea.server.engine.accounts;

import atrea.server.engine.networking.databases.EGender;
import lombok.Getter;

public class CharacterGeneralData {
    private @Getter String name;
    private @Getter EGender gender;
    private @Getter int level;
    private @Getter int guild;
    private @Getter ELocation location;

    public CharacterGeneralData() {
    }

    public CharacterGeneralData(String name, EGender gender, int level, int guild, ELocation location) {
        this.name = name;
        this.gender = gender;
        this.level = level;
        this.guild = guild;
        this.location = location;
    }
}