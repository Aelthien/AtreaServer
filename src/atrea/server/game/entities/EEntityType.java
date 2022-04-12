package atrea.server.game.entities;

public enum EEntityType {
    NPC,
    Player,
    Object;

    public static EEntityType integerToEnum(int value) {
        return values()[value];
    }
}