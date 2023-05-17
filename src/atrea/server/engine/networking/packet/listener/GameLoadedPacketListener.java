package atrea.server.engine.networking.packet.listener;

import atrea.server.engine.accounts.Account;
import atrea.server.engine.main.GameManager;
import atrea.server.engine.networking.session.Session;
import atrea.server.game.entities.EInteractableType;
import atrea.server.game.entities.Entity;
import io.netty.buffer.ByteBuf;

public class GameLoadedPacketListener implements IPacketListener {

    @Override
    public void process(Session session, ByteBuf buffer) {
        Entity player = GameManager.getEntityManager().createEntity(EInteractableType.PLAYER, -1);

        Account account = session.getAccount();
        account.setCurrentCharacter(player);

        session.getDatabaseManager().loadCharacter(player, account.getCurrentCharacterData());
        session.setGameLoaded(true);
    }
}