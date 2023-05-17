package atrea.server.game.content.items;

import atrea.server.game.content.interactions.EInteractionOption;
import atrea.server.game.content.items.ItemContainer.EContainerType;
import atrea.server.game.data.definition.ItemDefinition;
import atrea.server.game.entities.ecs.equipment.EquipmentComponent;
import atrea.server.game.entities.ecs.inventory.InventoryComponent;
import atrea.server.game.entities.ecs.skill.SkillsComponent;
import atrea.server.game.entities.ecs.systems.SystemManager;

public class ItemManager {

    private ItemDefinition[] itemDefinitions = new ItemDefinition[10000];

    private SystemManager systemManager;

    public ItemManager(SystemManager systemManager) {
        this.systemManager = systemManager;
    }

    public void interact(int entityId, int itemId, EInteractionOption option, EContainerType containerType, int slot) {
        ItemDefinition itemDefinition = itemDefinitions[itemId];

        switch (option) {
            case EQUIP:
                InventoryComponent inventoryComponent = systemManager.getInventorySystem().getComponent(entityId);
                EquipmentComponent equipmentComponent = systemManager.getEquipmentSystem().getComponent(entityId);
                SkillsComponent skillComponent = systemManager.getSkillsSystem().getComponent(entityId);

                Item item = inventoryComponent.getItemContainer().getItem(slot);

                if (skillComponent.hasSkills(itemDefinition.getRequiredSkills())) {
                    inventoryComponent.getItemContainer().removeItem(item, true);
                    equipmentComponent.equipItem(item, itemDefinition.getEquipmentSlot());
                }
                break;
            case OPEN:
                break;
            default:
                break;
        }
    }
}