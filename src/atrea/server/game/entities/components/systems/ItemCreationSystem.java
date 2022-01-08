package atrea.server.game.entity.components.systems;

import atrea.server.game.entity.components.ItemCreationComponent;

import java.util.HashMap;
import java.util.Map;

public class ItemCreationSystem extends ComponentSystem {

    private Map<Integer, ItemCreationComponent> components = new HashMap<>();

    private InventorySystem inventorySystem;
    private SkillSystem skillSystem;

    public void initialize(InventorySystem inventorySystem, SkillSystem skillSystem) {
        System.out.println("Initializing item creation system.");
        this.inventorySystem = inventorySystem;
        this.skillSystem = skillSystem;
    }

    public boolean createItem(int recipeId, int entityId) {
        //InventoryComponent inventory = inventorySystem.getInventory(entityId);

        //if (!inventory.isEmpty())
        //    return false;

        return false;
    }

    @Override public void update() {

    }
}
