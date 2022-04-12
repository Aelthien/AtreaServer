package atrea.server.engine.accounts;

public enum ELocation {
    EMERALD_ISLE;

    public static ELocation getLocation(byte value) {
        for (int i = 0; i < values().length; i++) {
            if (i == value)
                return values()[i];
        }

        return null;
    }
}