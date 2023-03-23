package atrea.server.engine.world;

import atrea.server.engine.utilities.Position;
import atrea.server.game.entities.ecs.EFacing;
import lombok.Getter;

public class EntitySpawnData {

    private @Getter int id;
    private @Getter EFacing facing;
    private @Getter Position position;

    public EntitySpawnData(int id, EFacing facing, Position position) {
        this.id = id;
        this.facing = facing;
        this.position = position;
    }
}