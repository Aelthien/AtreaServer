package atrea.server.engine.networking.packet.listener;

import atrea.server.engine.accounts.CharacterData;
import atrea.server.game.entities.EEntityType;
import atrea.server.game.entities.ecs.Entity;
import atrea.server.engine.main.GameManager;
import atrea.server.engine.networking.packet.outgoing.EnterGamePacket;
import atrea.server.engine.networking.session.ESessionState;
import atrea.server.engine.networking.session.Session;
import io.netty.buffer.ByteBuf;

public class PlayCharacterPacketListener implements IPacketListener {

    @Override
    public void process(Session session, ByteBuf buffer) {
        int characterSlot = buffer.readByte();

        CharacterData character = session.getAccount().getCharacters()[characterSlot];

        ESessionState state = session.getSessionState();

        /*
        boolean invalidRequest = state == IN_GAME
                || state == ENTERING_GAME
                || state == REQUESTED_LOG_OUT
                || state == LOGGING_OUT
                || state == LOGGED_OUT
                || state == LOGGED_IN
                || state == LOGGING_IN;
        */

        boolean canEnter = character != null
                && session.isLoggedIn();

        if (canEnter) {
            session.getMessageSender().send(new EnterGamePacket(true));
            session.getAccount().setCurrentCharacterData(character);
        } else
            session.getMessageSender().sendLogOut();
    }
}