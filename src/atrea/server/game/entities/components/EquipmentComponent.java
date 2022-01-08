package atrea.server.game.entity.components;

import atrea.server.game.content.items.Item;
import atrea.server.game.content.items.ItemContainer;
import atrea.server.game.content.items.EEquipmentSlot;
import atrea.server.game.data.definition.ItemDefinition;
import atrea.server.game.entity.Entity;

public class EquipmentComponent extends EntityComponent {

    private ItemContainer equipment;

    public EquipmentComponent(Entity parent) {
        super(parent);
    }

    public void addItem(Item item) {
        EEquipmentSlot slot = ItemDefinition.getDefinition(item.getId()).getEquipmentSlot();

        equipment.setItem(item, slot.ordinal());
    }
}