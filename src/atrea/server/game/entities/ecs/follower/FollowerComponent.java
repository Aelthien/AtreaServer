package atrea.server.game.entities.ecs.follower;

import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.EComponentType;
import atrea.server.game.entities.ecs.EntityComponent;
import lombok.Getter;

import static atrea.server.game.entities.ecs.EComponentType.*;

public class FollowerComponent extends EntityComponent {

    private int maxFollowers = 4;
    private int[] followers;
    private int followerCount;

    public FollowerComponent(Entity parent) {
        super(parent);

        followers = new int[maxFollowers];

        for (int i = 0; i < followers.length; i++) {
            followers[i] = -1;
        }
    }

    @Override public EComponentType getComponentType() {
        return FOLLOWER;
    }

    @Override public void reset() {

    }

    public void addFollower(int id) {
        for (int i = 0; i < followers.length; i++) {
            if (followers[i] == -1) {
                followers[i] = id;
                followerCount++;
                break;
            }
        }
    }

    public void removeFollower(int id) {
        for (int i = 0; i < followers.length; i++) {
            if (followers[i] == id) {
                followers[i] = -1;
                followerCount--;
                break;
            }
        }
    }

    public boolean hasMaxFollowerCount() {
        return followerCount == maxFollowers;
    }
}