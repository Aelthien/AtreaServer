package atrea.server.engine.networking.packet.outgoing;

import atrea.server.engine.accounts.CharacterData;
import atrea.server.engine.networking.databases.DataSerialiser;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;

import static atrea.server.engine.networking.packet.outgoing.OutgoingPacketConstants.*;

public class UpdateCharactersPacket extends OutgoingPacket {

    @Override public int getCode() {
        return UPDATE_CHARACTERS;
    }

    public UpdateCharactersPacket(CharacterData[] characters) {
        super();

        for (int i = 0; i < characters.length; i++) {
            buffer.writeByte(i);

            boolean empty = characters[i] == null;
            buffer.writeByte(empty ? 1 : 0);

            if (!empty) {
                List<byte[]> dataList = DataSerialiser.characters.serialise(characters[i]);
                buffer.writeBytes(dataList.get(0));
            }
        }
    }
}