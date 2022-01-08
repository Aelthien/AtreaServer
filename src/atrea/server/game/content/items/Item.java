package atrea.game.content.items;

import io.netty.buffer.ByteBuf;
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

    public Item(int id, int slot, int amount, int quality, int charges, int condition, Item[] mods) {
        this.id = id;
        this.slot = slot;
        this.amount = amount;
        this.quality = quality;
        this.charges = charges;
        this.condition = condition;
        this.mods = mods;
    }

    public Item() {
        this.id = -1;
        this.amount = 0;
    }

    public Item(int id, int amount) {
        this.id = id;
        this.amount = amount;
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
        return id == item.id && amount == item.amount && quality == item.quality && charges == item.charges && condition == item.condition && Arrays.equals(mods, item.mods);
    }

    @Override public int hashCode() {
        int result = Objects.hash(id, amount, quality, charges, condition);
        result = 31 * result + Arrays.hashCode(mods);
        return result;
    }
}