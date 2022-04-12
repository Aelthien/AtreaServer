package atrea.server.engine.networking.packet.outgoing;

import atrea.server.engine.accounts.CharacterData;
import atrea.server.engine.networking.databases.DataSerialiser;

import java.util.List;

import static atrea.server.engine.networking.packet.outgoing.OutgoingPacketConstants.ENTER_GAME;
import static atrea.server.engine.networking.packet.outgoing.OutgoingPacketConstants.UPDATE_CHARACTERS;

public class EnterGamePacket extends OutgoingPacket {

    @Override public int getCode() {
        return ENTER_GAME;
    }

    public EnterGamePacket(boolean canEnter) {
        super();

        System.out.println("Enter game");
        buffer.writeBoolean(canEnter);
    }
}