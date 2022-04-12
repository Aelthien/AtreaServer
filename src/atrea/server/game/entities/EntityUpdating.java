package atrea.server.game.entities;

import atrea.server.engine.main.GameManager;
import atrea.server.game.content.items.Item;
import atrea.server.game.entities.components.*;
import atrea.server.game.entities.components.systems.SystemManager;
import atrea.server.game.entities.components.systems.TransformSystem;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static atrea.server.engine.networking.packet.outgoing.OutgoingPacketConstants.SEND_ENTITY_STATUS;
import static atrea.server.game.entities.EEntityType.*;
import static atrea.server.game.entities.components.EComponentType.INVENTORY;
import static atrea.server.game.entities.components.EComponentType.TRANSFORM;
import static atrea.server.game.entities.components.EComponentUpdateType.DO_NOT_UPDATE_CLIENT;

public class EntityUpdating {

    private SystemManager systemManager;
    private Entity currentPlayer;
    private ByteBuf currentBuffer;

    public EntityUpdating() {
        systemManager = GameManager.getSystemManager();
    }

    public boolean updatePlayerLocalEntities(Entity player, Entity[] entities, ByteBuf buffer) {
        currentPlayer = player;
        currentBuffer = buffer;

        TransformSystem transformSystem = systemManager.getTransformSystem();

        List<Entity> updatedEntities = new ArrayList<>();
        List<Entity> addedEntities = new ArrayList<>();
        List<Entity> removedEntities = new ArrayList<>();

        int updates = 0;

        for (Iterator<Entity> entityIterator = currentPlayer.getLocalEntities().iterator(); entityIterator.hasNext(); ) {
            Entity entity = entityIterator.next();

            if (entity == null)
                continue;

            if (GameManager.getEntityManager().entityExists(entity.getEntityId())
                    && transformSystem.entitiesInRange(currentPlayer, entity, 50)) {
                updatedEntities.add(entity);
            } else {
                entityIterator.remove();
                removedEntities.add(entity);
            }

            updates++;
        }

        for (Entity entity : entities) {
            if (entity == null || entity.isHidden())
                continue;

            if (transformSystem.entitiesInRange(currentPlayer, entity, 50)) {
                if (!currentPlayer.getLocalEntities().contains(entity)) {
                    currentPlayer.getLocalEntities().add(entity);
                    addedEntities.add(entity);
                    updates++;
                }
            }
        }

        if (updates > 0) {
            buffer.writeByte(addedEntities.size());
            buffer.writeByte(removedEntities.size());
            buffer.writeByte(updatedEntities.size());

            for (Entity entity : addedEntities) {
                addEntity(entity);
            }

            for (Entity entity : removedEntities) {
                removeEntity(entity);
            }

            for (Entity entity : updatedEntities) {
                updateEntity(entity);
            }

            return true;
        }

        return false;
    }

    private void removeEntity(Entity entity) {
        currentBuffer.writeInt(entity.getEntityId());
    }

    private void addEntity(Entity entity) {
        currentBuffer.writeInt(entity.getEntityId());
        currentBuffer.writeByte(entity.getEntityType().ordinal());

        if (entity.getEntityType() != Player)
            currentBuffer.writeInt(entity.getDefinitionId());
        else
            currentBuffer.writeBoolean(entity == currentPlayer);

        addComponentData(entity, entity.getComponents());
    }

    private void updateEntity(Entity entity) {
        currentBuffer.writeInt(entity.getEntityId());
        currentBuffer.writeByte(entity.getEntityType().ordinal());

        addComponentData(entity, entity.getUpdateFlags());
    }

    private void addComponentData(Entity entity, List<EComponentType> components) {
        int entityId = entity.getEntityId();

        List<EComponentType> componentList = components.stream().filter(componentType -> componentType.getUpdateType() != DO_NOT_UPDATE_CLIENT).collect(Collectors.toList());

        int componentCount = componentList.size();

        currentBuffer.writeByte(componentCount);

        if (componentCount == 0)
            return;

        for (EComponentType component : componentList) {
            switch (component) {
                case TRANSFORM:
                    addTransformData(entityId);
                    break;
                case STATUS:
                    //addStatusData(entityId);
                    break;
                case INVENTORY:
                    //addInventoryData(entityId);
                    break;
                case BANK:
                    //addBankData(entityId);
                    break;
                case EQUIPMENT:
                    //addEquipmentData(entityId);
                    break;
                default:
                    break;
            }
        }
    }

    private void addInventoryData(int entityId) {
        InventoryComponent inventory = systemManager.getInventorySystem().get(entityId);
        currentBuffer.writeByte(INVENTORY.ordinal());
        int inventorySize = inventory.getInventory().getSize();
        currentBuffer.writeByte(inventorySize);

        for (int i = 0; i < inventorySize; i++) {
            Item item = inventory.getInventory().getItem(i);

            if (item != null) {
                currentBuffer.writeInt(item.getId());
                currentBuffer.writeInt(item.getAmount());
                currentBuffer.writeInt(item.getCharges());
                currentBuffer.writeInt(item.getCondition());
            } else
                currentBuffer.writeByte(-1);
        }
    }

    private void addBankData(int entityId) {
        InventoryComponent inventory = systemManager.getInventorySystem().get(entityId);
        currentBuffer.writeByte(INVENTORY.ordinal());
        int inventorySize = inventory.getInventory().getSize();
        currentBuffer.writeByte(inventorySize);

        for (int i = 0; i < inventorySize; i++) {
            Item item = inventory.getInventory().getItem(i);

            if (item != null && item.isNeedsRefresh()) {
                currentBuffer.writeInt(item.getId());
                currentBuffer.writeInt(item.getAmount());
                currentBuffer.writeInt(item.getCharges());
                currentBuffer.writeInt(item.getCondition());
                item.setNeedsRefresh(false);
            } else
                currentBuffer.writeByte(-1);
        }
    }

    private void addEquipmentData(int entityId) {
        EquipmentComponent equipment = systemManager.getEquipmentSystem().get(entityId);
        currentBuffer.writeByte(INVENTORY.ordinal());
        int inventorySize = equipment.getEquipment().getSize();
        currentBuffer.writeByte(inventorySize);

        for (int i = 0; i < inventorySize; i++) {
            Item item = equipment.getEquipment().getItem(i);

            if (item != null) {
                currentBuffer.writeInt(item.getId());
                currentBuffer.writeByte(item.getSlot());
                currentBuffer.writeInt(item.getAmount());
                currentBuffer.writeInt(item.getCharges());
                currentBuffer.writeInt(item.getCondition());
            } else
                currentBuffer.writeByte(-1);
        }
    }

    public void addTransformData(int entityId) {
        TransformComponent transform = systemManager.getTransformSystem().get(entityId);
        currentBuffer.writeByte(TRANSFORM.ordinal());
        currentBuffer.writeInt(transform.getPosition().getX());
        currentBuffer.writeInt(transform.getPosition().getY());
        currentBuffer.writeByte(transform.getPosition().getHeight());
        currentBuffer.writeBoolean(transform.isTeleport());
        currentBuffer.writeBoolean(transform.isRunning());
        currentBuffer.writeBoolean(transform.isPathEnd());
    }

    public void addStatusData(int entityId) {
        StatusComponent status = systemManager.getStatusSystem().get(entityId);
        currentBuffer.writeByte(SEND_ENTITY_STATUS);
        currentBuffer.writeInt(status.getHealth());
        currentBuffer.writeByte(status.getEnergy());
        currentBuffer.writeByte(status.getEffects().size());

        for (var effect : status.getEffects()) {
            if (effect.getEffect().isUpdateClient()) {
                currentBuffer.writeByte(effect.getEffect().ordinal());
                currentBuffer.writeInt(effect.getValue());
                currentBuffer.writeInt(effect.getTimeRemaining());
                currentBuffer.writeByte(effect.getValue());
            }
        }
    }
}
