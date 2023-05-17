package atrea.server.game.entities.ecs.equipment;

import atrea.server.engine.accounts.CharacterEquipmentData;
import atrea.server.game.content.items.Item;
import atrea.server.game.entities.Entity;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.bank.BankSystem;
import atrea.server.game.entities.ecs.inventory.InventorySystem;
import atrea.server.game.entities.ecs.skill.SkillsSystem;
import atrea.server.game.entities.ecs.status.StatusSystem;
import atrea.server.game.entities.ecs.systems.ComponentSystem;

public class EquipmentSystem extends ComponentSystem<EquipmentComponent> {

    private InventorySystem inventorySystem;
    private BankSystem bankSystem;
    private SkillsSystem skillSystem;
    private StatusSystem statusSystem;

    public void initialize(InventorySystem inventorySystem, BankSystem bankSystem, SkillsSystem skillSystem, StatusSystem statusSystem) {
        System.out.println("Initializing equipment system.");
        this.inventorySystem = inventorySystem;
        this.bankSystem = bankSystem;
        this.skillSystem = skillSystem;
        this.statusSystem = statusSystem;
    }

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        componentsArray[entity.getEntityId()] = new EquipmentComponent(entity);
    }

    @Override public void update() {

    }

    public void setEquipment(int entityId, Item[] items) {
        getComponent(entityId).setItems(items);
    }

    public void setEquipment(int entityId, CharacterEquipmentData equipmentData) {
        setEquipment(entityId, equipmentData.getEquipment().toArray(new Item[0]));
    }
}
