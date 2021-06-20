package atrea.game.content.items;

import atrea.game.data.definition.ItemDefinition;
import atrea.game.world.World;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ItemContainer {

    protected @Getter EContainerType containerType;
    protected @Getter Item[] items;
    protected @Getter int size;

    public ItemContainer(int size, EContainerType containerType) {
        this.size = size;
        this.containerType = containerType;

        items = new Item[size];

        for (int i = 0; i < size; i++) {
            items[i] = new Item(-1, 0);
        }
    }

    public boolean addItem(Item item) {
        if (!item.isValid())
            return false;

        ItemDefinition definition = ItemDefinition.getDefinition(item.getId());

        if (definition.isStackable()) {
            int maxStack = definition.getMaxStack();
            int index = -1;

            for (int i = 0; i < items.length; i++) {
                addItemToStack(item, i, maxStack);

                index = i;
                break;
            }

            if (index == -1) {
                int empty = emptySlot();

                if (empty != -1) {
                    //addItemToSlot(item, empty);
                    return true;
                }
            }

            int totalAmount = items[index].getAmount() + item.getAmount();
            int remainder;

            if (totalAmount > maxStack) {
                items[index].setAmount(maxStack);

                remainder = totalAmount - maxStack;

                System.out.println(totalAmount + " " + remainder);

                addItem(new Item(item.getId(), remainder));
            }

            if (totalAmount == maxStack) {
                items[index].setAmount(maxStack);

                //if (refresh)
                //    refreshItems();

                return true;
            }

            if (totalAmount < maxStack) {
                items[index].setAmount(totalAmount);

                //if (refresh)
                //    refreshItems();

                return true;
            }

            /*if (index == -1) {
                if(getPlayer() != null) {
                    getPlayer().getPacketSender().sendMessage("You couldn't hold all those items.");
                }

                if (refresh)
                    refreshItems();
                return this;
            }*/
        } else {
            int empty = emptySlot();

            if (empty != -1)
                setItem(item, empty);
        }
        return true;
    }

    public int emptySlot() {
        for (int i = 0; i < size; i++) {
            if (items[i].getId() == -1) {
                return i;
            }
        }

        return -1;
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

        ItemDefinition definition = ItemDefinition.getDefinition(item1.getId());

        if (item1.getId() != item2.getId()) {
            items[slot1].reset();
            items[slot2] = item1;

            return true;
        }

        return addItemToStack(item1, slot2, definition.getMaxStack());
    }

    public boolean addItemToStack(Item item, int slot, int maxStack) {
        Item itemToAddTo = items[slot];

        if (itemToAddTo.getAmount() == maxStack)
            return false;

        int newAmount = itemToAddTo.getAmount() + item.getAmount();

        if (newAmount <= maxStack) {
            items[slot].setAmount(newAmount);
        }

        if (newAmount > maxStack) {
            int remaining = newAmount - maxStack;
            item.setAmount(maxStack);

            addItem(new Item(item.getId(), remaining));
        }

        return true;
    }

    public boolean addItem(int id, int amount) {
        return addItem(new Item(id, amount));
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

        World.spawnItem(item, 0, 0);
    }

    public boolean setItem(Item item, int slot) {
        if (item == null)
            return false;

        if (items == null)
            return false;

        items[slot] = item;

        return true;
    }

    public Item getItem(int slot) {
        return items[slot];
    }

    public enum EContainerType {
        INVENTORY,
        EQUIPMENT,
        BANK,
        SHOP,
        LOOT
    }
}