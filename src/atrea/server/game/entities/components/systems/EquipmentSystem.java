package atrea.server.game.entity.components.systems;

import atrea.server.game.entity.components.EntityComponent;
import atrea.server.game.entity.components.EquipmentComponent;

import java.util.HashMap;
import java.util.Map;

public class EquipmentSystem extends ComponentSystem {

    private Map<Integer, EquipmentComponent> components = new HashMap<>();

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

    @Override
    public void addComponent(EntityComponent component) {
        components.put(component.getParent().getId(), (EquipmentComponent) component);
    }

    @Override public void update() {

    }
}
