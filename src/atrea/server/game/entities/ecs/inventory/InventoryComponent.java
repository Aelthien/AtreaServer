package atrea.server.game.entities.ecs.inventory;

import atrea.server.game.content.items.Item;
import atrea.server.game.content.items.ItemContainer;
import atrea.server.game.entities.ecs.EComponentType;
import atrea.server.game.entities.Entity;
import atrea.server.game.entities.ecs.EntityComponent;
import lombok.Getter;

import static atrea.server.game.content.items.ItemContainer.EContainerType.INVENTORY;

public class InventoryComponent extends EntityComponent {

    private @Getter ItemContainer itemContainer;

    @Override public EComponentType getComponentType() {
        return EComponentType.INVENTORY;
    }

    @Override public void reset() {

    }

    public InventoryComponent(Entity parent, String[] values) {
        super(parent);
        itemContainer = new ItemContainer(Integer.parseInt(values[0]), INVENTORY);
    }

    public boolean addItem(Item item, boolean refresh) {
        if (refresh)
            parent.addUpdateFlag(EComponentType.INVENTORY);

        return itemContainer.addItem(item);
    }

    public boolean setItem(Item item, int slot, boolean refresh) {
        return itemContainer.setItem(item, slot);
    }

    public boolean isEmpty() {
        return itemContainer.isEmpty();
    }

    public void setItems(Item[] items) {
        for (int i = 0; i < items.length; i++) {
            itemContainer.setItem(items[i]);
        }
    }
}