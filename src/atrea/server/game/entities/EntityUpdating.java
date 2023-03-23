package atrea.server.game.entities;

import atrea.server.engine.main.GameManager;
import atrea.server.engine.world.RegionManager;
import atrea.server.game.content.items.Item;
import atrea.server.game.entities.ecs.*;
import atrea.server.game.entities.ecs.command.SpeechComponent;
import atrea.server.game.entities.ecs.systems.SystemManager;
import atrea.server.game.entities.ecs.systems.TransformSystem;
import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static atrea.server.engine.networking.packet.outgoing.OutgoingPacketConstants.SEND_ENTITY_STATUS;
import static atrea.server.game.entities.EEntityType.Player;
import static atrea.server.game.entities.ecs.EComponentType.*;
import static atrea.server.game.entities.ecs.EComponentUpdateType.DO_NOT_UPDATE_CLIENT;

public class EntityUpdating {

    private final SystemManager systemManager;
    private Entity currentPlayer;
    private ByteBuf currentBuffer;

    public EntityUpdating() {
        systemManager = GameManager.getSystemManager();
    }

    public boolean updatePlayerLocalEntities(Entity player, ByteBuf buffer) {
        currentPlayer = player;
        currentBuffer = buffer;

        EntityManager entityManager = GameManager.getEntityManager();
        TransformSystem transformSystem = systemManager.getTransformSystem();

        List<Integer> grid = RegionManager.getEntityGrid().retrieve(transformSystem.getComponent(player.getEntityId()).getPosition());

        List<Entity> updatedEntities = new ArrayList<>();
        List<Entity> addedEntities = new ArrayList<>();
        List<Entity> removedEntities = new ArrayList<>();

        int updates = 0;

        List<Integer> localEntities = currentPlayer.getLocalEntities();

        for (Iterator<Integer> localEntityIterator = localEntities.iterator(); localEntityIterator.hasNext(); ) {
            Entity entity = entityManager.getEntity(localEntityIterator.next());

            if (!grid.contains(entity.getEntityId())) {
                localEntityIterator.remove();
                removedEntities.add(entity);
                updates++;
            } else {
                if (entity.isNeedingUpdate()) {
                    updatedEntities.add(entity);
                    updates++;
                }
            }
        }

        for (int entityId : grid) {
            Entity entity = entityManager.getEntity(entityId);

            if (!currentPlayer.getLocalEntities().contains(entityId)) {
                currentPlayer.getLocalEntities().add(entityId);
                addedEntities.add(entity);

                updates++;
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
        else {
            currentBuffer.writeBoolean(entity == currentPlayer);
            entity.setAlreadySpawned(true);
        }

        addComponentData(entity, entity.getUpdateFlags());
    }

    private void updateEntity(Entity entity) {
        currentBuffer.writeInt(entity.getEntityId());
        currentBuffer.writeByte(entity.getEntityType().ordinal());

        addComponentData(entity, entity.getUpdateFlags());
    }

    private void addComponentData(Entity entity, Set<EComponentType> components) {
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
                case SPEECH:
                    //addSpeechData(entityId);
                    break;
                case STATUS:
                    //addStatusData(entityId);
                    break;
                case INVENTORY:
                    addInventoryData(entityId);
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

    private void addSpeechData(int entityId) {
        SpeechComponent speech = systemManager.getSpeechSystem().getComponent(entityId);
        currentBuffer.writeByte(SPEECH.ordinal());
        currentBuffer.writeByte(speech.getMessage().length());
        currentBuffer.writeCharSequence(speech.getMessage(), Charset.defaultCharset());
    }

    private void addInventoryData(int entityId) {
        InventoryComponent inventory = systemManager.getInventorySystem().getComponent(entityId);

        int inventorySize = inventory.getInventory().getSize();

        currentBuffer.writeByte(INVENTORY.ordinal());
        currentBuffer.writeByte(inventorySize);

        for (int i = 0; i < inventorySize; i++) {
            Item item = inventory.getInventory().getItem(i);

            currentBuffer.writeInt(item.getId());

            if (item.getId() != -1) {
                currentBuffer.writeByte(item.getAmount());
                //currentBuffer.writeByte(item.isHighQuality() ? 1 : 0);
                //urrentBuffer.writeByte(item.getCharges());
                //currentBuffer.writeByte(item.getCondition());
            }
        }
    }

    private void addBankData(int entityId) {
        InventoryComponent inventory = systemManager.getInventorySystem().getComponent(entityId);
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
        EquipmentComponent equipment = systemManager.getEquipmentSystem().getComponent(entityId);
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
        TransformComponent transform = systemManager.getTransformSystem().getComponent(entityId);
        currentBuffer.writeByte(TRANSFORM.ordinal());
        currentBuffer.writeInt(transform.getPosition().getX());
        currentBuffer.writeInt(transform.getPosition().getY());
        currentBuffer.writeByte(transform.getPosition().getHeight());
        currentBuffer.writeBoolean(transform.isTeleport());
        currentBuffer.writeBoolean(transform.isRunning());
        currentBuffer.writeBoolean(transform.isPathEnd());
        currentBuffer.writeBoolean(transform.isResetQueue());
    }

    public void addStatusData(int entityId) {
        StatusComponent status = systemManager.getStatusSystem().getComponent(entityId);
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
