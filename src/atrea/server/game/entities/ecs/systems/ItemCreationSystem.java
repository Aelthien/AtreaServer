package atrea.server.game.entities.ecs.systems;

import atrea.server.game.entities.Entity;
import atrea.server.game.data.definition.ComponentDefinition;
import atrea.server.game.entities.ecs.ItemCreationComponent;
import atrea.server.game.entities.ecs.inventory.InventorySystem;
import atrea.server.game.entities.ecs.skill.SkillsSystem;

import java.util.HashMap;
import java.util.Map;

public class ItemCreationSystem extends ComponentSystem {

    private Map<Integer, ItemCreationComponent> components = new HashMap<>();

    private InventorySystem inventorySystem;
    private SkillsSystem skillSystem;

    public void initialize(InventorySystem inventorySystem, SkillsSystem skillSystem) {
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

    @Override public void addComponent(ComponentDefinition definition, Entity entity) {
        components.put(entity.getEntityId(), new ItemCreationComponent(entity));
    }

    @Override public void update() {

    }
}
