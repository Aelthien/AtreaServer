package atrea.game.content.items;

import atrea.game.content.skills.ESkill;
import lombok.Getter;
import lombok.Setter;

public class RequiredSkill {

    private @Getter @Setter ESkill skill;
    private @Getter @Setter int level;
}
