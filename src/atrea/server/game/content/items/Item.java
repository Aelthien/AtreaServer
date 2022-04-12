package atrea.server.game.content.items;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;

public class Item {
    private @Getter @Setter int id;
    private @Getter @Setter int slot;
    private @Getter @Setter int amount;
    private @Getter @Setter int quality;
    private @Getter @Setter int charges;
    private @Getter @Setter int condition;
    private @Getter @Setter Item[] mods;
    private @Getter @Setter boolean needsRefresh;

    public Item(int id, int slot, int amount, int quality, int charges, int condition, Item[] mods, boolean refresh) {
        this.id = id;
        this.slot = slot;
        this.amount = amount;
        this.quality = quality;
        this.charges = charges;
        this.condition = condition;
        this.mods = mods;
        this.needsRefresh = refresh;
    }

    public Item() {
        this.id = -1;
        this.amount = 0;
    }

    public Item(int id, int amount, boolean refresh) {
        this.id = id;
        this.amount = amount;
        this.needsRefresh = refresh;
    }

    public boolean isValid() {
        return id >= 0 && amount >= 1;
    }

    public void reset() {
        id = -1;
        amount = 0;
        quality = -1;
        charges = 0;
        condition = 0;
        mods = null;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && quality == item.quality && Arrays.equals(mods, item.mods);
    }

    @Override public int hashCode() {
        int result = Objects.hash(id, amount, quality, charges, condition);
        result = 31 * result + Arrays.hashCode(mods);
        return result;
    }

    private Item[] copyMods() {
        if (mods == null)
            return null;

        Item[] modsCopy = new Item[mods.length];

        for (int i = 0; i < mods.length; i++) {
            modsCopy[i] = mods[i].copy();
        }

        return modsCopy;
    }

    public Item copy() {
        return copy(-1);
    }

    public Item copy(int slot) {
        return new Item(id, slot, amount, quality, charges, condition, copyMods(), false);
    }
}