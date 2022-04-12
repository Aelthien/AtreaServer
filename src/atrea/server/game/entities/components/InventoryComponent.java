package atrea.server.game.entities.components;

import atrea.server.game.content.items.Item;
import atrea.server.game.content.items.ItemContainer;
import lombok.Getter;

import static atrea.server.game.content.items.ItemContainer.EContainerType.INVENTORY;

public class InventoryComponent extends EntityComponent {

    private @Getter ItemContainer inventory;

    @Override public EComponentType getComponentType() {
        return EComponentType.INVENTORY;
    }

    public InventoryComponent(Entity parent) {
        super(parent);
        inventory = new ItemContainer(25, INVENTORY);
    }

    @Override public void update() {

    }

    public boolean addItem(Item item, boolean refresh) {
        return inventory.addItem(item);
    }

    public boolean setItem(Item item, int slot, boolean refresh) {
        return inventory.setItem(item, slot);
    }

    public boolean isEmpty() {
        return inventory.isEmpty();
    }
}