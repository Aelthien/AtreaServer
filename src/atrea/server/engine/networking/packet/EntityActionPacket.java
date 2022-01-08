package atrea.server.networking.packet;

public class EntityActionPacket {
    private int entityId;
    private int actionIndex;

    public EntityActionPacket(int entityId, int actionIndex) {
        this.entityId = entityId;
        this.actionIndex = actionIndex;
    }
}