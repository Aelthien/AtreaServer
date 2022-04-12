package atrea.server.game.entities.components;

import atrea.server.game.entities.EntityUpdating;
import atrea.server.game.entities.components.systems.SystemManager;
import atrea.server.engine.main.GameManager;
import atrea.server.engine.networking.session.Session;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.data.definition.DefinitionManager;
import atrea.server.game.data.definition.EntityDefinition;
import atrea.server.game.entities.EEntityType;
import lombok.Getter;

import java.util.*;

import static atrea.server.game.entities.components.EComponentType.*;
import static atrea.server.game.entities.EEntityType.*;

public class EntityManager {

    private int MAX_ENTITY_COUNT = 5000;
    private final int MAX_QUEUE_OPERATIONS = 50;
    public static final int LOCAL_ENTITY_RANGE = 30;

    private final Entity[] entities = new Entity[50000];

    private int[] players;
    private int[] npcs;
    private int[] objects;

    private final Queue<Entity> entityRemoveQueue = new ArrayDeque<>();
    private final Queue<Entity> entityAddQueue = new ArrayDeque<>();

    private int entityCount;
    private int nextEntityId;
    private final SystemManager systemManager;
    private @Getter EntityUpdating entityUpdating;

    public EntityManager(SystemManager systemManager) {
        this.systemManager = systemManager;
        MAX_ENTITY_COUNT = systemManager.MAX_COMPONENTS;
        entityUpdating = new EntityUpdating();

        
    }

    private final ComponentDefinition[] playerComponents = {
            new ComponentDefinition(MOVEMENT, null),
            new ComponentDefinition(TRANSFORM, null)
            //new ComponentDefinition(BANK, null),
            //new ComponentDefinition(INVENTORY, null),
            //new ComponentDefinition(EQUIPMENT, null),
            //new ComponentDefinition(INVENTORY, null),
            //new ComponentDefinition(SKILL, null),
            //new ComponentDefinition(STATUS, null),
            //new ComponentDefinition(ITEM_CREATION, null)
    };

    public void update() {
        //System.out.println("Updating entities at " + System.currentTimeMillis());

        for (int i = 0; i < MAX_QUEUE_OPERATIONS; i++) {
            Entity entity = entityRemoveQueue.poll();

            if (entity == null)
                break;

            removeEntity(entity);
        }

        for (int i = 0; i < MAX_QUEUE_OPERATIONS; i++) {
            Entity entity = entityAddQueue.poll();

            if (entity == null)
                break;

            addEntity(entity);
        }

        for (Entity player : players.values()) {
            Session session = GameManager.getSessionManager().getPlayerSession(player.getAccountId());

            if (session.isGameLoaded()) {
                session.getMessageSender().sendUpdatePlayerLocalEntities(player, entities);
            }
        }

        for (Entity entity : entities) {
            if (entity == null)
                continue;

            entity.clearFlags();
        }
    }

    private Entity addEntityComponents(Entity entity, ComponentDefinition[] components) {
        for (ComponentDefinition definition : components) {
            systemManager.addComponent(definition, entity);
        }

        return entity;
    }

    public Entity createPlayer() {
        return createEntity(getNextEntityId(),-1, Player, 0, 0);
    }

    public Entity createEntity(int entityId, int definitionId, EEntityType type, int x, int y) {
        Entity entity = new Entity();

        entity.setEntityId(entityId);
        entity.setEntityType(type);

        if (type == Player)
            configurePlayer(entity);
        else
            configureEntity(entity, definitionId, x, y);

        return entity;
    }

    private void configureEntity(Entity entity,int definitionId, int x, int y) {
        entity.setDefinitionId(definitionId);

        addEntityComponents(entity, DefinitionManager.getEntityDefinition(definitionId).getComponents());

        systemManager.getTransformSystem().e
        queueEntityAdd(entity);
    }

    private void configurePlayer(Entity player) {
        addEntityComponents(player, playerComponents);

        queueEntityAdd(player);
    }

    public void queueEntityRemove(Entity entity) {
        entityRemoveQueue.add(entity);
    }

    public boolean queueEntityAdd(Entity entity) {
        if (canAddEntity()) {
            entityAddQueue.add(entity);
            return true;
        }

        return false;
    }

    private void removeEntity(Entity entity) {
        if (entity.getEntityType() == Player)
            players.remove(entity.getEntityId());
        else if (entity.getEntityType() == NPC)
            npcs.remove(entity.getEntityId());
        else
            objects.remove(entity.getEntityId());

        entities[entity.getEntityId()] = null;
        entityCount--;
    }

    private void addEntity(Entity entity) {

        System.out.println("Add entity");

        switch (entity.getEntityType()) {
            case  Player:
                players.put(entity.getEntityId(), entity);
                break;
            case NPC:
                npcs.put(entity.getEntityId(), entity);
                break;
            case Object:
                objects.put(entity.getEntityId(), entity);
                break;
        }

        entities[entity.getEntityId()] = entity;
        entityCount++;
    }

    private boolean canAddEntity() {
        return entityAddQueue.size() + entityCount < MAX_ENTITY_COUNT;
    }

    public boolean entityExists(int index) {
        return entities[index] != null;
    }

    public int getNextEntityId() {
        int next = nextEntityId;
        nextEntityId++;
        return next;
    }

    public Entity getEntity(int id) {
        return entities[id];
    }
}