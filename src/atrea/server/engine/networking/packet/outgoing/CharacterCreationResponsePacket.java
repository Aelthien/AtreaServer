package atrea.server.engine.networking.packet.outgoing;

import atrea.server.engine.networking.databases.ECharacterCreationStatus;

import static atrea.server.engine.networking.packet.outgoing.OutgoingPacketConstants.*;

public class CharacterCreationResponsePacket extends OutgoingPacket {

    @Override public int getCode() {
        return CHARACTER_CREATION_RESPONSE;
    }

    public CharacterCreationResponsePacket(ECharacterCreationStatus status) {
        super();

        buffer.writeByte(status.ordinal());
    }
}
