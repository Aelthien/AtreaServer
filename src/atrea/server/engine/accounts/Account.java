package atrea.server.engine.accounts;

import atrea.server.game.entities.Entity;
import lombok.Getter;
import lombok.Setter;

public class Account {

    private @Getter int id;
    private @Getter @Setter EAccountRole role;
    private @Getter @Setter CharacterData[] characters;
    private @Getter @Setter int[] characterIds;
    private @Getter @Setter CharacterData currentCharacterData;
    private @Getter Entity currentCharacter;

    /**
     * Creates a new account with the given id, role, character ids and characters.
     *
     * @param id   The id of the account.
     * @param role The role of the account.
     * @param characterIds The ids of the characters on this account.
     * @param characters The characters on this account.
     */
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