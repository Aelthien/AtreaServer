package atrea.server.engine.accounts;

import lombok.Getter;

public class PlayerGeneralData {
    private @Getter String name;
    private @Getter int level;
    private @Getter int guild;

    public PlayerGeneralData(String name, int level, int guild) {
        this.name = name;
        this.level = level;
        this.guild = guild;
    }
}