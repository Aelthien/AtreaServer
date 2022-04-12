package atrea.server.engine.networking.packet.listener;

import atrea.server.engine.accounts.CharacterData;
import atrea.server.engine.networking.databases.ECharacterCreationStatus;
import atrea.server.engine.networking.databases.EGender;
import atrea.server.engine.networking.packet.outgoing.CharacterCreationResponsePacket;
import atrea.server.engine.networking.packet.outgoing.UpdateCharactersPacket;
import atrea.server.engine.networking.session.Session;
import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;
import java.sql.SQLException;

import static atrea.server.engine.networking.databases.ECharacterCreationStatus.*;
import static atrea.server.engine.networking.databases.EGender.*;

public class CreateCharacterPacketListener implements IPacketListener {

    @Override
    public void process(Session session, ByteBuf buffer) {
        int index = buffer.readByte();

        int nameLength = buffer.readByte();

        String name  = buffer.readCharSequence(nameLength, Charset.defaultCharset()).toString();

        EGender gender = buffer.readByte() == 0 ? MALE : FEMALE;

        CharacterData characterData = session.getDatabaseManager().createCharacter(index, name, gender);

        ECharacterCreationStatus creationStatus;

        if (characterData != null) {
            creationStatus = SUCCESS;
            session.getMessageSender().send(new UpdateCharactersPacket(session.getAccount().getCharacters()));
        } else {
            creationStatus = FAIL;
        }

        session.getMessageSender().send(new CharacterCreationResponsePacket(creationStatus));
    }
}