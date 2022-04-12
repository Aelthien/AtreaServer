package atrea.server.game.content.skills;

import lombok.Getter;

public enum ESkill {
    MELEE(0,"melee"),
    ARCHERY(1,"archery"),
    THAUMAT(2,"thaumat"),
    POWER(3,"power"),
    DEFENCE(4,"defence"),
    CONSTITUTION(5,"constitution"),
    FAVOUR(6,"favour"),
    ALCHEMY(7,"alchemy"),
    LUMBERING(8,"lumbering"),
    MINING(9,"mining"),
    SMITHING(10,"smithing"),
    WOODWORKING(11,"woodworking"),
    LARCENY(12,"larceny");

    private @Getter int index;
    private @Getter String name;
    private @Getter static String[] names;

    ESkill(int index, String name) {
        this.index = index;
        this.name = name;
    }

    static
    {
        int length = ESkill.values().length;

        names = new String[length];

        for (ESkill skill : ESkill.values())
        {
            names[skill.ordinal()] = skill.name;
        }
    }

    public static ESkill getSkill(int index) {
        for (ESkill skill : ESkill.values()) {
            if (skill.index == index)
                return skill;
        }

        return ESkill.values()[ESkill.values().length];
    }
}