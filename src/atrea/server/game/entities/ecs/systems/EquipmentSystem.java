package atrea.server.game.entities.ecs.systems;

import atrea.server.game.entities.ecs.Entity;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.EquipmentComponent;

public class EquipmentSystem extends ComponentSystem<EquipmentComponent> {

    private InventorySystem inventorySystem;
    private BankSystem bankSystem;
    private SkillSystem skillSystem;
    private StatusSystem statusSystem;

    public void initialize(InventorySystem inventorySystem, BankSystem bankSystem, SkillSystem skillSystem, StatusSystem statusSystem) {
        System.out.println("Initializing equipment system.");
        this.inventorySystem = inventorySystem;
        this.bankSystem = bankSystem;
        this.skillSystem = skillSystem;
        this.statusSystem = statusSystem;
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        components[entity.getEntityId()] = new EquipmentComponent(entity);
    }

    @Override public void update() {

    }
}