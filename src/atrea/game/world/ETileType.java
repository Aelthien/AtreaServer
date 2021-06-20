package atrea.game.world;

public enum ETileType {

    GRASS(0),
    DIRT(1),
    SAND(2),
    LAVA(3),
    WATER(4),
    ROCK(5),
    MUD(6);

    public int index;

    ETileType(int index) {
        this.index = index;
    }

    public static ETileType getType(int index) {

        for (ETileType type : values()) {
            if (type.index == index)
                return type;
        }

        return ETileType.WATER;
    }
}