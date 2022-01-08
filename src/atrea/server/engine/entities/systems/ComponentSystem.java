package atrea.server.game.entities.components.systems;

import atrea.server.engine.entities.EntityManager;
import atrea.server.engine.entities.components.EntityComponent;
import lombok.Getter;

public abstract class ComponentSystem {

    private @Getter EntityManager entityManager;

    public void addComponent(EntityComponent component) {

    }

    public void removeComponent(int id) {

    }

    public abstract void update();
}