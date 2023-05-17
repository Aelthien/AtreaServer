package atrea.server.game.entities.ecs.inventory;

import atrea.server.engine.accounts.CharacterInventoryData;
import atrea.server.game.content.items.Item;
import atrea.server.game.entities.Entity;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.bank.BankSystem;
import atrea.server.game.entities.ecs.systems.ComponentSystem;
import atrea.server.game.entities.ecs.equipment.EquipmentSystem;

public class InventorySystem extends ComponentSystem<InventoryComponent> {

    private EquipmentSystem equipmentSystem;
    private BankSystem bankSystem;

    public void initialize(BankSystem bankSystem, EquipmentSystem equipmentSystem) {
        this.bankSystem = bankSystem;
        this.equipmentSystem = equipmentSystem;

        componentsArray = new InventoryComponent[5000];
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        InventoryComponent component = new InventoryComponent(entity, definition.getValues());
        componentsArray[component.getId()] = component;
    }

    @Override public void update() {

    }

    public void setInventory(int entityId, Item[] items) {
        getComponent(entityId).setItems(items);
    }

    public void setInventory(int entityId, CharacterInventoryData inventoryData) {

    }
}