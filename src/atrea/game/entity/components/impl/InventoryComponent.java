package atrea.game.entity.components.impl;

import atrea.game.content.items.Item;
import atrea.game.content.items.ItemContainer;
import atrea.game.entity.Entity;
import atrea.game.entity.Player;
import atrea.game.entity.components.EntityComponent;
import lombok.Getter;

import static atrea.game.content.items.ItemContainer.EContainerType.INVENTORY;

public class InventoryComponent extends EntityComponent {

    private @Getter ItemContainer inventory;

    public InventoryComponent(Entity parent) {
        super(parent);
        inventory = new ItemContainer(25, INVENTORY);
    }

    public boolean addItem(Item item, boolean refresh) {
        if (inventory.addItem(item)) {
            if (refresh && parent != null)
                ((Player) parent).getPlayerSession().getMessageSender().sendItem(item, -1, INVENTORY);

            return true;
        }

        return false;
    }

    public boolean setItem(Item item, int slot, boolean refresh) {

        if (inventory.setItem(item, slot)) {
            if (parent instanceof Player && refresh)
                ((Player) parent).getPlayerSession().getMessageSender().sendItem(item, slot, INVENTORY);

            return true;
        }

        return false;
    }
}