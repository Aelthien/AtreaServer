package atrea.server.game.entities.ecs.bank;

import atrea.server.game.entities.Entity;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.systems.ComponentSystem;
import atrea.server.game.entities.ecs.equipment.EquipmentSystem;
import atrea.server.game.entities.ecs.inventory.InventorySystem;

public class BankSystem extends ComponentSystem<BankComponent> {

    private InventorySystem inventorySystem;
    private EquipmentSystem equipmentSystem;

    public void initialize(InventorySystem inventorySystem, EquipmentSystem equipmentSystem) {
        this.inventorySystem = inventorySystem;
        this.equipmentSystem = equipmentSystem;
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        componentsArray[entity.getEntityId()] = new BankComponent(entity);
    }

    @Override public void update() {

    }
}