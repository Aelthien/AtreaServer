package atrea.server.engine.accounts;

import lombok.Getter;

public enum EAccountRole {
    USER(0, "User"),
    FOUNDER(1, "Founder"),
    DEVELOPER(2, "Developer"),
    MODERATOR(3, "Moderator"),
    COMMUNITY_MODERATOR(4, "Community_Moderator");

    private @Getter int index;
    private @Getter String name;

    EAccountRole(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static EAccountRole getRole(int index) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].getIndex() == index)
                return values()[i];
        }

        return null;
    }

    public static EAccountRole getRole(String name) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].getName() == name)
                return values()[i];
        }

        return null;
    }
}