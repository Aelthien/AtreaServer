package atrea.server.engine.networking.packet.outgoing;

import atrea.server.engine.accounts.CharacterData;
import atrea.server.engine.networking.databases.DataSerialisers;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.SQLException;

import static atrea.server.engine.networking.packet.outgoing.OutgoingPacketConstants.*;

public class UpdateCharactersPacket extends OutgoingPacket {

    @Override public int getCode() {
        return UPDATE_CHARACTERS;
    }

    public UpdateCharactersPacket(CharacterData[] characters) throws SQLException {
        super();

        for (int i = 0; i < characters.length; i++) {
            buffer.writeByte(i);

            boolean empty = characters[i] == null;

            buffer.writeByte(empty ? 1 : 0);

            ByteBuf newBuffer = PooledByteBufAllocator.DEFAULT.buffer();

            if (!empty) {
                SerialBlob blob = DataSerialisers.general.Serialise(characters[i].getGeneralData(), newBuffer);

                newBuffer.writeBytes(blob.getBytes(1, (int) blob.length()));
                buffer.writeBytes(newBuffer);
                newBuffer.release();
            }
        }
    }
}