package atrea.server.game.entities.ecs;

import atrea.server.game.content.items.Item;
import atrea.server.game.content.items.ItemContainer;
import lombok.Getter;

import java.util.List;

import static atrea.server.game.content.items.ItemContainer.EContainerType.INVENTORY;

public class InventoryComponent extends EntityComponent {

    private @Getter ItemContainer inventory;

    @Override public EComponentType getComponentType() {
        return EComponentType.INVENTORY;
    }

    public InventoryComponent(Entity parent, String[] values) {
        super(parent);
        inventory = new ItemContainer(Integer.parseInt(values[0]), INVENTORY);
    }

    public boolean addItem(Item item, boolean refresh) {
        if (refresh)
            parent.addUpdateFlag(EComponentType.INVENTORY);

        return inventory.addItem(item);
    }

    public boolean setItem(Item item, int slot, boolean refresh) {
        return inventory.setItem(item, slot);
    }

    public boolean isEmpty() {
        return inventory.isEmpty();
    }
}