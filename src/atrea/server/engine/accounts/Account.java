package atrea.server.engine.accounts;

import atrea.server.game.entities.ecs.Entity;
import lombok.Getter;
import lombok.Setter;

public class Account {

    private @Getter int id;
    private @Getter @Setter EAccountRole role;
    private @Getter @Setter CharacterData[] characters;
    private @Getter @Setter int[] characterIds;
    private @Getter @Setter CharacterData currentCharacterData;
    private @Getter Entity currentCharacter;

    public Account(int id, EAccountRole role, int[] characterIds, CharacterData[] characters) {
        this.id = id;
        this.role = role;
        this.characterIds = characterIds;
        this.characters = characters;
    }

    public void setCurrentCharacter(Entity currentCharacter) {
        this.currentCharacter = currentCharacter;
        currentCharacter.setAccountId(id);
    }
}