package atrea.server.game.entities.ecs;

import atrea.server.engine.main.GameEngine;
import atrea.server.engine.utilities.Delegate;
import atrea.server.game.entities.EEntityType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Entity {

    private @Getter @Setter int definitionId = -1;
    private @Getter @Setter int entityId;
    private @Getter @Setter int accountId = -1;
    private @Getter @Setter EEntityType entityType;
    private @Getter @Setter int state;
    private @Getter @Setter String name = "";
    private @Getter @Setter boolean neverSpawn;
    private @Getter @Setter boolean alreadySpawned;
    private @Getter @Setter int level;
    private @Getter @Setter boolean registered;
    private @Getter @Setter boolean hidden;
    private Delegate onAddDelegate;
    private Delegate onRemoveDelegate;
    private @Getter @Setter boolean needsSpawning = true;

    private @Getter List<EComponentType> components = new ArrayList<>();

    private @Getter Set<EComponentType> updateFlags = new HashSet<>();

    private @Getter @Setter List<Integer> localEntities = new ArrayList<>();

    public Entity(int entityId, EEntityType type) {
        this.entityId = entityId;
        this.entityType = type;
    }

    public void clearFlags() {
        updateFlags.clear();
    }

    public void onAdd() {
        if (onAddDelegate != null) {
            onAddDelegate.execute();
        }
    }

    public void onRemove() {
        if (onRemoveDelegate != null) {
            onRemoveDelegate.execute();
        }
    }

    public void setOnAddDelegate(Delegate delegate) {
        if (onAddDelegate == null)
            onAddDelegate = delegate;
    }
    public void setOnRemoveDelegate(Delegate delegate) {
        if (onRemoveDelegate == null)
            onRemoveDelegate = delegate;
    }

    public void addUpdateFlag(EComponentType updateFlag) {
        updateFlags.add(updateFlag);
    }

    public boolean isNeedingUpdate() {
        return !updateFlags.isEmpty();
    }

    public void removeUpdateFlag(EComponentType updateFlag) {
        updateFlags.remove(updateFlag);
    }

    public void addComponent(EComponentType componentType) {
        components.add(componentType);
    }
}