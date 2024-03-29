package atrea.server.game.entities.ecs;

import lombok.Getter;

import static atrea.server.game.entities.ecs.EComponentUpdateType.*;

public enum EComponentType {
    TRANSFORM(UPDATE_CLIENT),
    MOVEMENT(DO_NOT_UPDATE_CLIENT),
    GATHERABLE(DO_NOT_UPDATE_CLIENT),
    INVENTORY(UPDATE_CLIENT),
    BANK(UPDATE_CLIENT),
    EQUIPMENT(UPDATE_CLIENT),
    SKILLS(UPDATE_CLIENT),
    ITEM_CREATION(DO_NOT_UPDATE_CLIENT),
    STATUS(UPDATE_CLIENT),
    CHAT(UPDATE_CLIENT),
    COMBAT(UPDATE_CLIENT),
    GUILD(UPDATE_CLIENT),
    AI(UPDATE_CLIENT),
    INTERACTION(DO_NOT_UPDATE_CLIENT),
    SPEECH(UPDATE_CLIENT),
    COMMAND(DO_NOT_UPDATE_CLIENT),
    FOLLOWER(DO_NOT_UPDATE_CLIENT),
    SUMMON(DO_NOT_UPDATE_CLIENT);

    private @Getter EComponentUpdateType updateType;

    EComponentType(EComponentUpdateType updateType) {

        this.updateType = updateType;
    }
}