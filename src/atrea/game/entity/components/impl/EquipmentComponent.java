package atrea.game.entity.components.impl;

import atrea.game.content.items.Item;
import atrea.game.content.items.ItemContainer;
import atrea.game.data.definition.EEquipmentSlot;
import atrea.game.data.definition.ItemDefinition;
import atrea.game.entity.Entity;
import atrea.game.entity.components.EntityComponent;

public class EquipmentComponent extends EntityComponent {

    private ItemContainer equipment;

    public EquipmentComponent(Entity parent) {
        super(parent);
    }

    public void addItem(Item item) {
        EEquipmentSlot slot = ItemDefinition.getDefinition(item.getId()).geteEquipmentSlot();

        equipment.setItem(item, slot.ordinal());
    }
}