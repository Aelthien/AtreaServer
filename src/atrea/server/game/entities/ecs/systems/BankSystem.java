package atrea.server.game.entities.ecs.systems;

import atrea.server.game.entities.ecs.Entity;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.BankComponent;

public class BankSystem extends ComponentSystem<BankComponent> {

    private InventorySystem inventorySystem;
    private EquipmentSystem equipmentSystem;

    public void initialize(InventorySystem inventorySystem, EquipmentSystem equipmentSystem) {
        this.inventorySystem = inventorySystem;
        this.equipmentSystem = equipmentSystem;
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        components[entity.getEntityId()] = new BankComponent(entity);
    }

    @Override public void update() {

    }
}