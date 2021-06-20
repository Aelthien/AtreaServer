package atrea.game.entity.components.impl;

import atrea.game.entity.DatabaseProcedure;
import atrea.game.entity.Entity;
import atrea.game.entity.PlayerDatabaseProcedure;
import atrea.game.entity.components.EntityComponent;
import atrea.net.database.DatabaseManager;

public class NetworkComponent extends EntityComponent {
    private DatabaseProcedure databaseProcedure;
    private DatabaseManager databaseManager;

    public NetworkComponent(Entity parent, DatabaseProcedure databaseProcedure) {
        super(parent);
        this.databaseProcedure = databaseProcedure;
        this.databaseManager = new DatabaseManager();
    }

    public NetworkComponent(Entity parent, PlayerDatabaseProcedure databaseProcedure) {
        super(parent);
        this.databaseProcedure = databaseProcedure;
        this.databaseManager = new DatabaseManager();
    }

    public void save()
    {
        databaseManager.save(databaseProcedure);
    }
}