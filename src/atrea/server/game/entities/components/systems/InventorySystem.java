package atrea.server.game.entities.components.systems;

import atrea.server.game.entities.components.Entity;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.components.InventoryComponent;

public class InventorySystem extends ComponentSystem<InventoryComponent> {

    private EquipmentSystem equipmentSystem;
    private BankSystem bankSystem;

    public void initialize(BankSystem bankSystem, EquipmentSystem equipmentSystem) {
        this.bankSystem = bankSystem;
        this.equipmentSystem = equipmentSystem;
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {

    }

    @Override public void update() {

    }
}