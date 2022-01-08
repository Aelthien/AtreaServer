package atrea.game.content.guilds;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Guild {
    private static int MAX_MEMBER_COUNT = 500;

    private @Getter int id;
    private @Getter String name;
    private @Getter List<Integer> members = new ArrayList<>();
    private @Getter @Setter boolean disbanded;

    public boolean addMember(int id) {
        if (members.size() >= MAX_MEMBER_COUNT)
            return false;

        members.add(id);
        return true;
    }

    public boolean removeMember(int id) {
        if (!members.contains(id))
            return false;

        members.remove(id);
        return true;
    }
}