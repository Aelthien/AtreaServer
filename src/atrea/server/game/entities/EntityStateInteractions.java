package atrea.server.game.entities;

import atrea.server.game.content.interactions.EInteractionOption;
import lombok.Getter;

import java.util.Locale;

public class EntityStateInteractions {

    private @Getter int state;
    private @Getter EInteractionOption[] interactions;
}