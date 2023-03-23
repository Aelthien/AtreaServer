package atrea.server.game.entities.ecs.systems;

import atrea.server.game.entities.ecs.Entity;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.InventoryComponent;

public class InventorySystem extends ComponentSystem<InventoryComponent> {

    private EquipmentSystem equipmentSystem;
    private BankSystem bankSystem;

    public void initialize(BankSystem bankSystem, EquipmentSystem equipmentSystem) {
        this.bankSystem = bankSystem;
        this.equipmentSystem = equipmentSystem;

        components = new InventoryComponent[5000];
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        InventoryComponent component = new InventoryComponent(entity, definition.getValues());
        components[component.getId()] = component;
    }

    @Override public void update() {

    }
}