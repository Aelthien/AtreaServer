package atrea.game.entity;

import atrea.game.entity.components.EntityComponent;
import atrea.main.Position;
import atrea.server.PlayerSession;
import lombok.Getter;
import lombok.Setter;

public class Player extends Entity {

   private String email;
   private String password;

   private boolean loggedIn;

   private @Getter @Setter PlayerSession playerSession;

   private final Position START_POSITION = new Position(0, 0);

   public Player() {
   }

   @Override
   public void sequence() {
      playerSession.update();

      for (EntityComponent component : components) {
         component.update();
      }
   }
}