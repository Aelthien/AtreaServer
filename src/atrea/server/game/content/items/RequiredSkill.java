package atrea.server.game.content.items;

import atrea.server.game.content.skills.ESkill;
import lombok.Getter;
import lombok.Setter;

public class RequiredSkill {

    private @Getter @Setter ESkill skill;
    private @Getter @Setter int level;
}
