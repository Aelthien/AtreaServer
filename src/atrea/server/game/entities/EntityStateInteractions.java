package atrea.server.game.entity;

import atrea.server.game.content.interactions.EInteractionOption;
import lombok.Getter;

public class EntityStateInteractions {

    private @Getter int state;
    private @Getter EInteractionOption[] interactions;
}