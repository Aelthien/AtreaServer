package atrea.server.engine.accounts;

import atrea.server.engine.networking.databases.EGender;
import lombok.Getter;

public class CharacterGeneralData {
    private @Getter String name;
    private @Getter EGender gender;
    private @Getter int age;
    private @Getter int level;
    private @Getter int guild;
    private @Getter ELocation location;

    public CharacterGeneralData() {
    }

    public CharacterGeneralData(String name, EGender gender, int age, ELocation location) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.location = location;
    }
}