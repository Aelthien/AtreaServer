package atrea.game.content.items;

import java.util.Arrays;
import java.util.Objects;

public class Item
{
    private int id;
    private int amount;
    private int quality;
    private int charges;
    private int condition;
    private int[] components;

    public Item() {
        this.id = -1;
        this.amount = 0;
    }

    public Item(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public boolean isValid()
    {
        return id >= 0 && amount >= 1;
    }

    public void reset()
    {
        id = -1;
        amount = 0;
        quality = 0;
        charges = 0;
        condition = 0;
        components = null;
    }

    public int getId() {
        return id;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && amount == item.amount && quality == item.quality && charges == item.charges && condition == item.condition && Arrays.equals(components, item.components);
    }

    @Override public int hashCode() {
        int result = Objects.hash(id, amount, quality, charges, condition);
        result = 31 * result + Arrays.hashCode(components);
        return result;
    }
}