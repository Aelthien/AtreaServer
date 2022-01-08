package atrea.server.game.entity.components;

import atrea.server.game.content.items.Item;
import atrea.server.game.content.items.ItemContainer;
import atrea.server.game.entity.EEntityType;
import atrea.server.game.entity.Entity;
import atrea.server.game.world.GameManager;
import lombok.Getter;

import static atrea.server.game.content.items.ItemContainer.EContainerType.INVENTORY;

public class InventoryComponent extends EntityComponent {

    private @Getter ItemContainer inventory;

    public InventoryComponent(Entity parent) {
        super(parent);
        inventory = new ItemContainer(25, INVENTORY);
    }

    public boolean addItem(Item item, boolean refresh) {
        if (inventory.addItem(item)) {
            if (refresh && parent != null)
                GameManager.getPlayerSessionManager().getPlayerSession(parent).getMessageSender().sendItem(item, -1, INVENTORY);

            return true;
        }

        return false;
    }

    public boolean setItem(Item item, int slot, boolean refresh) {

        if (inventory.setItem(item, slot)) {
            if (parent.getEntityType() == EEntityType.Player && refresh)
                GameManager.getPlayerSessionManager().getPlayerSession(parent).getMessageSender().sendItem(item, slot, INVENTORY);

            return true;
        }

        return false;
    }

    public boolean isEmpty() {
        return inventory.isEmpty();
    }
}