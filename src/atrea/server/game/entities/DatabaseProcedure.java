package atrea.server.game.entities;

import atrea.server.game.entities.ecs.Entity;

import java.sql.Connection;

public abstract class DatabaseProcedure
{
    private Entity entity;
    private Connection connection;

    public DatabaseProcedure(Entity entity)
    {
        this.entity = entity;
    }

    public void load(Connection connection){}
    public void save(Connection connection){}
}