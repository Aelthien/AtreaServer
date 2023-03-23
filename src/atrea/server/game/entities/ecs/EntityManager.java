package atrea.server.game.entities.ecs;

import atrea.server.engine.world.RegionManager;
import atrea.server.game.entities.EntityUpdating;
import atrea.server.game.entities.ecs.systems.SystemManager;
import atrea.server.engine.main.GameManager;
import atrea.server.engine.networking.session.Session;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.data.definition.DefinitionManager;
import atrea.server.game.entities.EEntityType;
import lombok.Getter;

import java.util.*;

import static atrea.server.game.entities.ecs.EComponentType.*;
import static atrea.server.game.entities.EEntityType.*;

public class EntityManager {

    public static int MAX_ENTITY_COUNT = 5000;
    private int MAX_NPC_COUNT = 2000;
    private int MAX_PLAYER_COUNT = 1000;
    private int MAX_OBJECT_COUNT = MAX_ENTITY_COUNT - MAX_PLAYER_COUNT - MAX_NPC_COUNT;

    private final int MAX_QUEUE_OPERATIONS = 50;
    public static final int LOCAL_ENTITY_RANGE = 30;

    private final Entity[] entities = new Entity[MAX_ENTITY_COUNT];

    private Stack<Integer> playerIds = new Stack<>();
    private Stack<Integer> characterIds = new Stack<>();
    private Stack<Integer> objectIds = new Stack<>();

    private Queue<Integer> freeIds = new ArrayDeque<>();

    private final Queue<Entity> entityRemoveQueue = new ArrayDeque<>();
    private final Queue<Entity> entityAddQueue = new ArrayDeque<>();

    private int entityCount;
    private int nextEntityId;
    private final SystemManager systemManager;
    private @Getter EntityUpdating entityUpdating;

    public EntityManager(SystemManager systemManager) {
        this.systemManager = systemManager;
        entityUpdating = new EntityUpdating();

        for (int i = 0; i < MAX_ENTITY_COUNT; i++) {
            freeIds.add(i);
        }
    }

    private final ComponentDefinition[] playerComponents = {
            new ComponentDefinition(MOVEMENT, null),
            new ComponentDefinition(TRANSFORM, null),
            //new ComponentDefinition(COMMAND, null),
            //new ComponentDefinition(BANK, null),
            new ComponentDefinition(INVENTORY, new String[]{"25"})
            //new ComponentDefinition(EQUIPMENT, null),
            //new ComponentDefinition(SKILL, null),
            //new ComponentDefinition(STATUS, null),
            //new ComponentDefinition(ITEM_CREATION, null)
    };

    public void update() {
//        System.out.println(entityAddQueue.size() + " " + System.currentTimeMillis());
        //System.out.println("Update 1");
        for (int i = 0; i < MAX_QUEUE_OPERATIONS; i++) {
            Entity entity = entityRemoveQueue.poll();

            if (entity == null)
                break;

            removeEntity(entity);
        }

        //System.out.println("Update 2");
            for (int i = 0; i < MAX_QUEUE_OPERATIONS; i++) {
                Entity entity = entityAddQueue.poll();

                if (entity == null)
                    break;

                addEntity(entity);
            }

        //System.out.println(playerIds.size());
        for (int playerId : playerIds) {
            Entity player = entities[playerId];

            Session session = GameManager.getSessionManager().getPlayerSession(player.getAccountId());

            if (session.isGameLoaded()) {
                session.getMessageSender().sendTickData();
                session.getMessageSender().sendUpdatePlayerLocalEntities(player);
            }
        }

        //System.out.println("Update 4");
        for (Entity entity : entities) {
            if (entity == null)
                continue;

            entity.clearFlags();
        }
    }

    public Entity createEntity(EEntityType type, int definitionId) {
        Entity entity = new Entity(getNextEntityId(), type);

        if (type == Player) {
            addEntityComponents(entity, playerComponents);
        } else {
            addEntityComponents(entity, DefinitionManager.getEntityDefinition(definitionId).getComponents());
        }

        queueEntityAdd(entity);

        return entity;
    }

    private void addEntityComponents(Entity entity, ComponentDefinition[] components) {
        for (ComponentDefinition definition : components) {
            systemManager.addComponent(definition, entity);
        }
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
        entities[entity.getEntityId()] = null;
        freeIds.add(entity.getEntityId());

        if (entity.getEntityType() == Player) {
            for (int i = 0; i < playerIds.size(); i++) {
                if (playerIds.get(i) == entity.getEntityId())
                    playerIds.remove(i);
            }
        } else if (entity.getEntityType() == NPC) {
            for (int i = 0; i < characterIds.size(); i++) {
                if (characterIds.get(i) == entity.getEntityId())
                    characterIds.remove(i);
            }
        } else {
            for (int i = 0; i < objectIds.size(); i++) {
                if (objectIds.get(i) == entity.getEntityId())
                    objectIds.remove(i);
            }
        }

        entityCount--;
    }

    private void addEntity(Entity entity) {
        int entityId = entity.getEntityId();

        switch (entity.getEntityType()) {
            case Player:
                registerPlayerId(entityId);
                break;
            case NPC:
                registerNpcId(entityId);
                break;
            case Object:
                registerObjectId(entityId);
                break;
            default:
                break;
        }

        RegionManager.getEntityGrid().insert(systemManager.getTransformSystem().getComponent(entityId).getPosition(), entityId);

        entities[entity.getEntityId()] = entity;
        entityCount++;
    }

    private void registerPlayerId(int entityId) {
        playerIds.add(entityId);

    }

    private void registerNpcId(int entityId) {
        characterIds.add(entityId);
    }

    private void registerObjectId(int entityId) {
        objectIds.add(entityId);
    }

    private boolean canAddEntity() {
        return entityCount < MAX_ENTITY_COUNT;
    }

    public boolean entityExists(int index) {
        return entities[index] != null;
    }

    public int getNextEntityId() {
        return freeIds.poll();
    }

    public Entity getEntity(int id) {
        return entities[id];
    }
}