package atrea.server.game.content.items;

import atrea.server.game.data.definition.DefinitionManager;
import atrea.server.game.data.definition.ItemDefinition;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ItemContainer {

    protected @Getter
    EContainerType containerType;
    protected @Getter
    Item[] items;
    protected @Getter
    int size;

    public ItemContainer(int size, EContainerType containerType) {
        this.size = size;
        this.containerType = containerType;

        items = new Item[size];

        for (int i = 0; i < size; i++) {
            items[i] = new Item(-1, 0, true);
        }
    }

    public int findPartialStack(Item item, int maxStack) {
        for (int i = 0; i < size; i++) {
            if (items[i].equals(item) && items[i].getAmount() < maxStack)
                return i;
        }

        return -1;
    }

    private boolean addItemToNewStack(Item item) {
        int emptySlot = findEmptySlot();

        if (emptySlot == -1)
            return false;

        setItem(item, emptySlot);

        return true;
    }

    public boolean addItem(Item item) {
        if (!item.isValid())
            return false;

        ItemDefinition definition = DefinitionManager.getItemDefinition(item.getId());

        if (!definition.isStackable())
            return addItemToNewStack(item);
        else {
            int index = findPartialStack(item, definition.getMaxStack());

            if (index != -1) {
                Item itemToAddTo = items[index];

                int newAmount = itemToAddTo.getAmount() + item.getAmount();
            } else {
                return addItemToNewStack(item);
            }
        }

        return false;
    }

    public boolean removeAll(Item[] items) {
        for (Item item : items) {
            remove(item);
        }

        return true;
    }

    public boolean remove(Item item) {
        return false;
    }

    public int findEmptySlot() {
        for (int i = 0; i < size; i++) {
            if (items[i].getId() == -1)
                return i;
        }

        return -1;
    }

    public boolean hasItem(Item item) {
        int amountRequired = item.getAmount();

        for (int i = 0; i < size; i++) {
            Item currentItem = items[i];

            if (currentItem.getId() == item.getId())
                amountRequired = amountRequired - currentItem.getAmount();
        }

        if (amountRequired > 0)
            return false;

        return true;
    }

    public int findItem(int id) {
        for (int i = 0; i < size; i++) {
            if (items[i].getId() == id) {
                return i;
            }
        }

        return -1;
    }

    public boolean swapToContainer(ItemContainer from, int fromSlot, int toSlot) {
        Item fromItem = from.getItem(fromSlot);

        return false;
    }

    public boolean swapToContainer(ItemContainer from, int fromSlot) {
        Item fromItem = from.getItem(fromSlot);


        return false;
    }

    public boolean mergeStacks(int slot1, int slot2) {
        Item item1 = items[slot1];
        Item item2 = items[slot2];

        if (!item1.isValid())
            return false;

        ItemDefinition definition = DefinitionManager.getItemDefinition(item1.getId());

        if (!item1.equals(item2)) {
            items[slot1] = item2;
            items[slot2] = item1;

            return true;
        }

        return addItemToStack(item1, slot2, definition.getMaxStack());
    }

    public boolean addItemToStack(Item item, int slot, int maxStack) {
        Item itemToAddTo = items[findPartialStack(item, maxStack)];

        if (itemToAddTo.getAmount() == maxStack)
            return false;

        int newAmount = itemToAddTo.getAmount() + item.getAmount();

        if (newAmount <= maxStack) {
            items[slot].setAmount(newAmount);
        } else if (newAmount > maxStack) {
            item.setAmount(newAmount - maxStack);
            return false;
        }

        return true;
    }

    public boolean addItem(int id, int amount) {
        return addItem(new Item(id, amount, true));
    }

    public boolean removeItem(int id, int amount, boolean refresh) {
        List<Item> foundItems = new ArrayList<>();

        int amountToRemove = amount;
        int newAmount;

        for (Item item : items) {
            newAmount = item.getAmount() - amountToRemove;

            if (amountToRemove == 0)
                return true;
        }

        return false;
    }

    public void dropItem(int slot, int amount) {
        Item item = getItem(slot);

        if (getItem(slot) == null)
            return;

        if (amount <= 0)
            return;

        //GameManager.spawnItem(item, 0, 0);
    }

    public boolean setItem(Item item, int slot) {
        if (!item.isValid())
            return false;

        items[slot] = item;

        return true;
    }

    public Item getItem(int slot) {
        return items[slot];
    }

    public boolean isEmpty() {
        return findEmptySlot() == -1;
    }

    public enum EContainerType {
        INVENTORY,
        EQUIPMENT,
        BANK,
        SHOP,
        LOOT
    }
}