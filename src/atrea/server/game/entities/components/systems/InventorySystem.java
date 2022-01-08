package atrea.server.game.entity.components.systems;

import atrea.server.game.entity.components.InventoryComponent;

import java.util.HashMap;
import java.util.Map;

public class InventorySystem extends ComponentSystem {

    private Map<Integer, InventoryComponent> components = new HashMap<>();

    private EquipmentSystem equipmentSystem;
    private BankSystem bankSystem;

    public void initialize(BankSystem bankSystem, EquipmentSystem equipmentSystem) {
        this.bankSystem = bankSystem;
        this.equipmentSystem = equipmentSystem;
    }

    public InventoryComponent getInventoryComponent(int id) {
        return components.get(id);
    }

    @Override public void update() {

    }
}