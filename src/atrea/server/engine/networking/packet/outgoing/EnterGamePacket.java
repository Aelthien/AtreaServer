package atrea.server.engine.networking.packet.outgoing;

import static atrea.server.engine.networking.packet.outgoing.OutgoingPacketConstants.ENTER_GAME;

public class EnterGamePacket extends OutgoingPacket {

    @Override public int getCode() {
        return ENTER_GAME;
    }

    public EnterGamePacket(boolean canEnter) {
        super();

        buffer.writeBoolean(canEnter);
    }
}