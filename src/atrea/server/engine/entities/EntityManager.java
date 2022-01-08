package atrea.server.game.entity;

import atrea.server.engine.main.SyncedGameExecutor;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.data.definition.DefinitionManager;
import atrea.server.game.data.definition.NPCDefinition;
import atrea.server.game.entity.components.*;
import atrea.server.game.entity.components.systems.*;
import lombok.Getter;

import java.util.*;

public class EntityManager {

    private final int MAX_ENTITY_COUNT = 5000;
    private Entity[] entities = new Entity[MAX_ENTITY_COUNT];
    private Queue<Entity> entityRemoveQueue = new ArrayDeque<>();
    private Queue<Entity> entityAddQueue = new ArrayDeque<>();
    private int entityCount;
    private int nextEntityId;

    private static SyncedGameExecutor executor = new SyncedGameExecutor();

    private @Getter MovementSystem movementSystem;
    private @Getter TransformSystem transformSystem;
    private @Getter
    InventorySystem inventorySystem;
    private @Getter
    BankSystem bankSystem;
    private @Getter
    EquipmentSystem equipmentSystem;
    private @Getter SkillSystem skillSystem;
    private @Getter ItemCreationSystem itemCreationSystem;
    private @Getter StatusSystem statusSystem;
    private @Getter
    ChatSystem chatSystem;
    private @Getter
    CombatSystem combatSystem;

    private ComponentDefinition[] playerComponents = {
                    new ComponentDefinition("transform", null),
                    new ComponentDefinition("movement", null),
                    new ComponentDefinition("bank", null),
                    new ComponentDefinition("inventory", null),
                    new ComponentDefinition("equipment", null),
                    new ComponentDefinition("inventory", null),
                    new ComponentDefinition("skill", null),
                    new ComponentDefinition("status", null),
                    new ComponentDefinition("itemCreation", null)
            };

    public EntityManager() {
        transformSystem = new TransformSystem();
        movementSystem = new MovementSystem();
        inventorySystem = new InventorySystem();
        bankSystem = new BankSystem();
        equipmentSystem = new EquipmentSystem();
        skillSystem = new SkillSystem();
        itemCreationSystem = new ItemCreationSystem();
        statusSystem = new StatusSystem();
        chatSystem = new ChatSystem();
        combatSystem = new CombatSystem();

        movementSystem.initialize(transformSystem);
        inventorySystem.initialize(bankSystem, equipmentSystem);
        equipmentSystem.initialize(inventorySystem, bankSystem, skillSystem, statusSystem);
        itemCreationSystem.initialize(inventorySystem, skillSystem);
        statusSystem.initialize(combatSystem);
        combatSystem.initialize(statusSystem);
    }

    public void update() {
        transformSystem.update();
        movementSystem.update();
        statusSystem.update();
        itemCreationSystem.update();
        inventorySystem.update();
        equipmentSystem.update();
        bankSystem.update();
        skillSystem.update();

        /*
        executor.sync(new SyncedGameTask(entityCount, MAX_ENTITY_COUNT, false) {
            @Override public boolean checkIndex(int index) {
                return entities[index] != null;
            }

            @Override public void execute(int index) {
                Entity entity = entities[index];
            }
        });*/
    }

    private Entity createEntity(ComponentDefinition[] components) {
        Entity entity = new Entity();
        entity.setEntityId(getNextEntityId());

        for (ComponentDefinition definition : components) {
            switch (definition.getType()) {
                case "transform":
                    transformSystem.addComponent(new TransformComponent(entity));
                    break;
                    case "movement":
                    movementSystem.addComponent(new MovementComponent(entity));
                    break;
                    case "bank":
                    bankSystem.addComponent(new BankComponent(entity));
                    break;
                    case "equipment":
                    equipmentSystem.addComponent(new EquipmentComponent(entity));
                    break;
                    case "inventory":
                    inventorySystem.addComponent(new InventoryComponent(entity));
                    break;
                    case "status":
                    statusSystem.addComponent(new StatusComponent(entity));
                    break;
                    case "itemCreation":
                    itemCreationSystem.addComponent(new ItemCreationComponent(entity));
                    break;
                default:
                    break;
            }
        }

        return entity;
    }

    public Entity createNPC(int id) {
        NPCDefinition definition = DefinitionManager.getNPCDefinition(id);

        Entity npc = createEntity(definition.getComponentDefinitions());
        npc.setName(definition.getName());
        npc.setId(id);
        npc.setEntityType(EEntityType.NPC);

        return npc;
    }

    public Entity createPlayer() {
        Entity player = createEntity(playerComponents);

        player.setEntityType(EEntityType.Player);

        return player;
    }

    public void queueEntityRemove(Entity entity) {
        entityRemoveQueue.add(entity);
    }

    public void queueEntityAdd(Entity entity) {
        entityAddQueue.add(entity);
    }

    private void removeEntity(Entity entity) {
        entities[entity.getEntityId()] = null;
        entityCount--;
    }

    private void addEntity(Entity entity) {
        entities[entity.getEntityId()] = entity;
        entityCount++;
    }

    public boolean entityExists(int index) {
        return entities[index] != null;
    }

    public int getNextEntityId() {
        int next = nextEntityId;
        nextEntityId++;
        return next;
    }
}